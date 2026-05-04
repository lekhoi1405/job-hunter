package Group.Artifact.domain.dto.response.user;

import Group.Artifact.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponse {
    private String name;
    private String email;
    private String password;

    public static UserResponse fromEntity(User user){
        return UserResponse.builder()
                            .name(user.getName())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build();
    }
}
