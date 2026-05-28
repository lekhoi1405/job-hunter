package Group.Artifact.domain.dto.login;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginResponse {
    private long id;
    private String email;
    private String name;
}
