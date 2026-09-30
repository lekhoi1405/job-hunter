package com.lekhoi.recruitment.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.lekhoi.recruitment.domain.dto.SkillDTO;
import com.lekhoi.recruitment.domain.entity.Skill;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface SkillMapper {
    Skill toEntity(SkillDTO.CreateRequest createRequest);
    SkillDTO.Response toResponse(Skill skill);
    
    @Mapping(target = "id", ignore = true)
    void update(SkillDTO.UpdateRequest updateRequest, @MappingTarget Skill skill);
}
