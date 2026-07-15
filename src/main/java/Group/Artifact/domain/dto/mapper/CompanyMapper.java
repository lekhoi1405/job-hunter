package Group.Artifact.domain.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.CompanyDTO;
import Group.Artifact.domain.entity.Company;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyMapper {

    Company toEntity(CompanyDTO.CreateRequest companyCreateRequest);
    CompanyDTO.Response toResponse(Company company);

    void update(CompanyDTO.UpdateRequest updateRequest, @MappingTarget Company company);
} 
