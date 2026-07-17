package Group.Artifact.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDTO {
    @NotBlank(message = "user khong de trong")
    private String username;
    
    @NotBlank(message = "pass khong de trong")
    private String password;
    
}
