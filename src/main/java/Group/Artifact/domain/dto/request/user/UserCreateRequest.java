package Group.Artifact.domain.dto.request.user;

import Group.Artifact.domain.entity.User;
import Group.Artifact.util.constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class UserCreateRequest {
    private String name;
    private String email;
    private String password;
    private int age;
    private String gender;
    private String address;

    public static User toEntity(UserCreateRequest createRequest){
        return User.builder().name(createRequest.getName())
                                .email(createRequest.getEmail())
                                .address(createRequest.getAddress())
                                .password(createRequest.getPassword())
                                .age(createRequest.getAge())
                                .gender(GenderEnum.valueOf(createRequest.getGender()))
                                .build();
    }
}
