package Group.Artifact.domain.dto.mapper;

import org.mapstruct.Mapper;

import Group.Artifact.domain.dto.CompanyDTO;
import Group.Artifact.domain.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyDTO.CompanyCreateRequest companyCreateRequest);
    
} 
