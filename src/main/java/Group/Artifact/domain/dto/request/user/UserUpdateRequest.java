package Group.Artifact.domain.dto.request.user;

import Group.Artifact.domain.entity.User;
import Group.Artifact.util.constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateRequest {
    
    private Long id;
    private String name;
    private String address;
    private Integer age;
    private String gender;

    public static void update(UserUpdateRequest userUpdateRequest, User user){
        if(userUpdateRequest.getName()!=null)user.setName(userUpdateRequest.getName());
        if(userUpdateRequest.getAddress()!=null)user.setAddress(userUpdateRequest.getAddress());
        if(userUpdateRequest.getAge()!=null)user.setAge(userUpdateRequest.getAge());
        if(userUpdateRequest.getGender()!=null)user.setGender(GenderEnum.valueOf(userUpdateRequest.getGender()));
    }
}