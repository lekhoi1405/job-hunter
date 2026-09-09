package Group.Artifact.service;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.SkillDTO;
import Group.Artifact.domain.entity.Skill;
import Group.Artifact.repository.SkillRepository;
import Group.Artifact.service.mapper.SkillMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    public SkillDTO.Response handleCreateSkill(SkillDTO.CreateRequest createRequest){
        Skill skill = this.skillMapper.toEntity(createRequest);
        return this.skillMapper.toResponse(this.skillRepository.save(skill));
    }
}
