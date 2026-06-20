package Group.Artifact.domain.dto.login;

import org.springframework.http.ResponseCookie;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResult {
    private final LoginDTOResponse loginDTOResponse;
    private final ResponseCookie responseCookie;
}
