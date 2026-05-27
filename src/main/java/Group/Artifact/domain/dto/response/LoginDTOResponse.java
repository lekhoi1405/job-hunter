package Group.Artifact.domain.dto.response;

import Group.Artifact.domain.dto.response.user.UserLoginResponse;
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
