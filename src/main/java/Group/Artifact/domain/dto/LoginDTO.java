package Group.Artifact.domain.dto;


import org.springframework.http.ResponseCookie;

import Group.Artifact.domain.dto.response.login.UserLoginResponse;
import jakarta.validation.constraints.NotBlank;

public interface LoginDTO {
    record Result (
        Response response,
        ResponseCookie responseCookie
    ){}

    record Request (
        @NotBlank(message = "user khong de trong")
        String username,
        
        @NotBlank(message = "pass khong de trong")
        String password
    ){}

    record Response (
        String accessToken,
        UserLoginResponse userLoginResponse
    ){}
}
