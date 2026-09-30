package com.lekhoi.recruitment.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lekhoi.recruitment.domain.dto.UserDTO;
import com.lekhoi.recruitment.domain.entity.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "companyId", source = "company.id")
    UserDTO.Response toResponse(User user);
    
    User toEntity(UserDTO.CreateRequest createRequest);

    @Mapping(target = "companyId", source = "company.id")
    UserDTO.CreateResponse toCreateResponse(User user);

    @Mapping(target = "id", ignore = true)
    void update(UserDTO.UpdateRequest updateRequest,  @MappingTarget User user);

    @Mapping(target = "companyId", source = "company.id")
    UserDTO.UpdateResponse toUpdateResponse(User user);
}
