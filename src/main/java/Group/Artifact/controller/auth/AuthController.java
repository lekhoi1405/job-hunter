package Group.Artifact.controller.auth;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.request.LoginDTO;
import Group.Artifact.domain.dto.login.LoginDTOResponse;
import Group.Artifact.domain.dto.login.LoginResult;
import Group.Artifact.domain.dto.login.UserLoginResponse;
import Group.Artifact.service.AuthService;
import Group.Artifact.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService; 

    @ApiMessage("Login success")
    @PostMapping("/login")
    public ResponseEntity<LoginDTOResponse> login(@Valid @RequestBody LoginDTO loginDTO){
        LoginResult loginResult = this.authService.handleVerifyUserLogin(loginDTO);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, loginResult.getResponseCookie().toString()).body(loginResult.getLoginDTOResponse());
    }

    @ApiMessage("Logout success")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refresh_token") String refreshToken){
        ResponseCookie responseCookie = this.authService.handleLogout(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, responseCookie.toString()).body(null);
    }

    @ApiMessage("Get account")
    @GetMapping("/account")
    public ResponseEntity<UserLoginResponse> getAccount(){
        return ResponseEntity.ok().body(this.authService.handleGetAccount());
    }

    @ApiMessage("Get refresh token")
    @GetMapping("/refresh")
    public ResponseEntity<LoginDTOResponse> refreshSession(@CookieValue("refresh_token") String refreshToken){
        LoginResult loginResult = this.authService.handleRefreshSession(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, loginResult.getResponseCookie().toString()).body(loginResult.getLoginDTOResponse());
    }
}   
