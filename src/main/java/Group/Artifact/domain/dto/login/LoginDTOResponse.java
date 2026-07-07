package Group.Artifact.domain.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginDTOResponse {
    private String accessToken;
    private UserLoginResponse userLoginResponse;
}
