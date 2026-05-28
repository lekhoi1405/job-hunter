package Group.Artifact.domain.dto.mapper;

import java.lang.annotation.Target;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.CompanyDTO;
import Group.Artifact.domain.entity.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toEntity(CompanyDTO.CreateRequest companyCreateRequest);
    CompanyDTO.Response toResponse(Company company);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(CompanyDTO.UpdateRequest updateRequest, @MappingTarget Company company);
} 
