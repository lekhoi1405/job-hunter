package com.lekhoi.recruitment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.lekhoi.recruitment.config.UserDetailsCustom;
import com.lekhoi.recruitment.domain.dto.LoginDTO;
import com.lekhoi.recruitment.domain.dto.response.login.UserLogin;
import com.lekhoi.recruitment.domain.dto.response.login.UserLoginResponse;
import com.lekhoi.recruitment.domain.entity.RefreshToken;
import com.lekhoi.recruitment.domain.entity.User;
import com.lekhoi.recruitment.util.SecurityUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class AuthService {

    @Value("${app.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public LoginDTO.Result handleVerifyUserLogin(LoginDTO.Request request){
        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        Authentication authentication 
            = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UserDetailsCustom userDetailsCustom = (UserDetailsCustom)authentication.getPrincipal();
        User user = userDetailsCustom.getUser();

        LoginDTO.Response response = this.handleCreateAccessToken(user);
        ResponseCookie responseCookie = this.handleCreateRefreshToken(user);

        return new LoginDTO.Result(response, responseCookie);
    }

    public LoginDTO.Response handleCreateAccessToken(User user){
        UserLoginResponse userLoginResponse = UserLoginResponse.builder()
                                                               .id(user.getId())
                                                               .email(user.getEmail())
                                                               .name(user.getName())
                                                               .build();
        String accessToken = this.securityUtil.createAccessToken(userLoginResponse);                                                       
        return new LoginDTO.Response(accessToken, userLoginResponse);
    }

    public ResponseCookie handleCreateRefreshToken(User user){     
        RefreshToken refreshToken = this.securityUtil.createRefreshToken();
        this.refreshTokenService.handleAddUser(user, refreshToken);

        ResponseCookie responseCookie = ResponseCookie.from("refresh_token", refreshToken.getToken())
                                                        .httpOnly(true)
                                                        .secure(true)
                                                        .path("/")
                                                        .maxAge(refreshTokenExpiration)
                                                        .build();
        return responseCookie;
    }

    @Transactional
    public LoginDTO.Result handleRefreshSession(String token){
        RefreshToken refreshToken = this.refreshTokenService.handleGetRefreshTokenByTokenWithUser(token);
        LoginDTO.Response response = this.handleCreateAccessToken(refreshToken.getUser());
        ResponseCookie responseCookie = this.handleCreateRefreshToken(refreshToken.getUser());
        this.refreshTokenService.handleDeleteTokenByToken(token);

        return new LoginDTO.Result(response, responseCookie);
    }

    public UserLoginResponse handleGetAccount(){
        UserLogin userLogin = SecurityUtil.getCurrentUser().orElseThrow(() -> new InternalError(""));

        User user = this.userService.handleGetUserByUsername(userLogin.getEmail());

        UserLoginResponse userLoginResponse = Optional.ofNullable(user)
                                                    .map(u -> UserLoginResponse.builder()
                                                                                .id(u.getId())
                                                                                .email(u.getEmail())
                                                                                .name(u.getName())
                                                                                .authorities(userLogin.getAuthorities())
                                                                                .build())
                                                    .orElse(null);
        return userLoginResponse;
    }

    @Transactional
    public ResponseCookie handleLogout(String token){
        this.refreshTokenService.handleDeleteTokenByToken(token);
        return ResponseCookie.from("refresh_token", null)
                                                        .httpOnly(true)
                                                        .secure(true)
                                                        .path("/")
                                                        .maxAge(0)
                                                        .build();
    }
}
 