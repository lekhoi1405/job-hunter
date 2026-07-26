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

import Group.Artifact.service.AuthService;
import Group.Artifact.domain.dto.LoginDTO;
import Group.Artifact.domain.dto.response.login.UserLoginResponse;
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
    public ResponseEntity<LoginDTO.Response> login(@Valid @RequestBody LoginDTO.Request loginDTO){
        LoginDTO.Result result = this.authService.handleVerifyUserLogin(loginDTO);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result.responseCookie().toString()).body(result.response());
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
    public ResponseEntity<LoginDTO.Response> refreshSession(@CookieValue("refresh_token") String refreshToken){
        LoginDTO.Result result = this.authService.handleRefreshSession(refreshToken);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, result.responseCookie().toString()).body(result.response());
    }
}   
