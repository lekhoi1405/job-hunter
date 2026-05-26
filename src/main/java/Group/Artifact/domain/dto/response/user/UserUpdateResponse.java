package Group.Artifact.domain.dto.response.user;

import java.time.Instant;

import Group.Artifact.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Builder
public class UserUpdateResponse {

    private Long id;
    private String name;
    private String address;
    private int age;
    private String gender;
    private Instant updatedAt;
    private String updatedBy; 

    public static UserUpdateResponse fromEntity(User user){
        return UserUpdateResponse.builder()
                            .id(user.getId())
                            .name(user.getName())
                            .address(user.getAddress())
                            .age(user.getAge())
                            .gender(user.getGender().toString())
                            .updatedAt(user.getUpdatedAt())
                            .updatedBy(user.getUpdatedBy())
                            .build();
    }
}
