package Group.Artifact.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.JobDTO;
import Group.Artifact.domain.entity.Job;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
public interface JobMapper {

    @Mapping(target =  "companyId", source = "company.id")
    JobDTO.Response toResponse(Job job);
    Job toEntity(JobDTO.CreateRequest createRequest);
    
    void update(JobDTO.UpdateRequest updateRequest, @MappingTarget Job job);
} 
