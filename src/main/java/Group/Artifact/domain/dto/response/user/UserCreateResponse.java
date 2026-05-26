package Group.Artifact.domain.dto.response.user;

import java.time.Instant;

import Group.Artifact.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserCreateResponse {

    private Long id;
    private String name;
    private String email;
    private String address;
    private int age;
    private String gender;
    private Instant createdAt;
    private String createdBy; 

    public static UserCreateResponse fromEntity(User user){
        return UserCreateResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .email(user.getEmail())
                            .address(user.getAddress())
                            .age(user.getAge())
                            .gender(user.getGender().toString())
                            .createdAt(user.getCreatedAt())
                            .createdBy(user.getCreatedBy())
                            .build();
    }
}
