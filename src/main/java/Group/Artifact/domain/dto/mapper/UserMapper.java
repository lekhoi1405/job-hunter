package Group.Artifact.domain.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.UserDTO;
import Group.Artifact.domain.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserDTO.Response toResponse(User user);
    
    User toEntity(UserDTO.CreateRequest createRequest);
    UserDTO.CreateResponse toCreateResponse(User user);

    void update(UserDTO.UpdateRequest updateRequest,  @MappingTarget User user);
    UserDTO.UpdateResponse toUpdateResponse(User user);
}
