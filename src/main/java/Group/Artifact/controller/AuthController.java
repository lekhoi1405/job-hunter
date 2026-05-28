package Group.Artifact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.request.LoginDTO;
import Group.Artifact.domain.dto.login.LoginDTOResponse;
import Group.Artifact.domain.dto.login.UserLoginResponse;
import Group.Artifact.domain.entity.User;
import Group.Artifact.service.UserService;
import Group.Artifact.util.SecurityUtil;
import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder,SecurityUtil securityUtil,UserService userService){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@Valid @RequestBody LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication 
            = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String accessToken = this.securityUtil.createToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = this.userService.handleGetUserByUsername(loginDTO.getUsername());
        
        LoginDTOResponse loginDTOResponse = LoginDTOResponse.builder()
                                                        .accessToken(accessToken)
                                                        .userLoginResponse(UserLoginResponse.builder()
                                                                                            .id(user.getId())
                                                                                            .email(user.getEmail())
                                                                                            .name(user.getName())
                                                                                            .build())
                                                        .build();
        return ResponseEntity.ok().body(loginDTOResponse);
    }   
}   
