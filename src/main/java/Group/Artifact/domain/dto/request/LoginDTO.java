package Group.Artifact.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "user khong de trong")
    private String username;
    
    @NotBlank(message = "pass khong de trong")
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
}
