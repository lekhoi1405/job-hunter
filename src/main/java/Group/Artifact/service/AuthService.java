package Group.Artifact.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import Group.Artifact.domain.dto.login.LoginDTOResponse;
import Group.Artifact.domain.dto.login.LoginResult;
import Group.Artifact.domain.dto.login.UserDetailsCustom;
import Group.Artifact.domain.dto.login.UserLoginResponse;
import Group.Artifact.domain.dto.request.LoginDTO;
import Group.Artifact.domain.entity.RefreshToken;
import Group.Artifact.domain.entity.User;
import Group.Artifact.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthService {

    @Value("${koiBong.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public LoginResult handleVerifyUserLogin(LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication 
            = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom)authentication.getPrincipal();
        User user = userDetailsCustom.getUser();

        LoginDTOResponse loginDTOResponse = this.handleCreateAccessToken(user);
        ResponseCookie responseCookie = this.handleCreateRefreshToken(user);

        return LoginResult.builder()
                            .loginDTOResponse(loginDTOResponse)
                            .responseCookie(responseCookie)
                            .build();
    }

    public LoginDTOResponse handleCreateAccessToken(User user){
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                                                               .id(user.getId())
                                                               .email(user.getEmail())
                                                               .name(user.getName())
                                                               .build();

        String accessToken = this.securityUtil.createAccessToken(userLoginResponse);                                                       

        LoginDTOResponse loginDTOResponse = LoginDTOResponse.builder()
                                                        .accessToken(accessToken)
                                                        .userLoginResponse(userLoginResponse)
                                                        .build();
        return loginDTOResponse;
    }

    public ResponseCookie handleCreateRefreshToken(User user){     
        RefreshToken refreshToken = this.securityUtil.createRefreshToken();
        this.refreshTokenService.handleAddUser(user.getId(), refreshToken);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken.getToken())
                                                        .httpOnly(true)
                                                        .secure(true)
                                                        .path("/")
                                                        .maxAge(refreshTokenExpiration)
                                                        .build();
        return responseCookie;
    }

    @Transactional
    public LoginResult handleRefreshSession(String token){
        RefreshToken refreshToken = this.refreshTokenService.handleGetRefreshTokenByToken(token);
        LoginDTOResponse loginDTOResponse = this.handleCreateAccessToken(refreshToken.getUser());
        ResponseCookie responseCookie = this.handleCreateRefreshToken(refreshToken.getUser());
        this.refreshTokenService.handleDeleteTokenByToken(refreshToken);

        return LoginResult.builder()
                            .loginDTOResponse(loginDTOResponse)
                            .responseCookie(responseCookie)
                            .build();
    }

    public UserLoginResponse handleGetAccount(){
        String email = SecurityUtil.getCurrentUser().orElseThrow(() -> new InternalError(""));

        User user = this.userService.handleGetUserByUsername(email);

        UserLoginResponse userLoginResponse = Optional.ofNullable(user)
                                                    .map(u -> UserLoginResponse.builder()
                                                                                .id(u.getId())
                                                                                .email(u.getEmail())
                                                                                .name(u.getName())
                                                                                .build())
                                                    .orElse(null);
        return userLoginResponse;
    }
}
 