package Group.Artifact.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.UserDTO;
import Group.Artifact.domain.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {CompanyMapper.class})
public interface UserMapper {
    @Mapping(target = "companyId", source = "company.id")
    UserDTO.Response toResponse(User user);
    
    User toEntity(UserDTO.CreateRequest createRequest);

    @Mapping(target = "companyId", source = "company.id")
    UserDTO.CreateResponse toCreateResponse(User user);

    @Mapping(target = "company", ignore = true)
    void update(UserDTO.UpdateRequest updateRequest,  @MappingTarget User user);

    @Mapping(target = "companyId", source = "company.id")
    UserDTO.UpdateResponse toUpdateResponse(User user);
}
