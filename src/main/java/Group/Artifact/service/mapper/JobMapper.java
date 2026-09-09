package Group.Artifact.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import Group.Artifact.domain.dto.JobDTO;
import Group.Artifact.domain.entity.Job;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) 
public interface JobMapper {
    JobDTO.Response toResponse(Job job);
    Job toEntity(JobDTO.CreateRequest createRequest);

    Job toEntity(JobDTO.CreateWithSkillRequest createWithSkillRequest);
} 
