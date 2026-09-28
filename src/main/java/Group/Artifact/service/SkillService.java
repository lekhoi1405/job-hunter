package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.SkillDTO;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.ResultPagination.Meta;
import Group.Artifact.domain.entity.Skill;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.repository.JobSkillRepository;
import Group.Artifact.repository.SkillRepository;
import Group.Artifact.service.mapper.SkillMapper;
import Group.Artifact.util.error.AlreadyExistsException;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class SkillService {
    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;
    private final JobSkillRepository jobSkillRepository;

    public SkillDTO.Response handleCreateSkill(SkillDTO.CreateRequest createRequest){
        if(this.skillRepository.existsByName(createRequest.name()))throw new AlreadyExistsException("Name has been exited");
        Skill skill = this.skillMapper.toEntity(createRequest);
        return this.skillMapper.toResponse(this.skillRepository.save(skill));
    }

    public ResultPagination<List<SkillDTO.Response>> handleGetAllskill(Integer current, Integer pageSize, String filterRequest){
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);
        String filter = filterRequest.trim();

        Specification<Skill> specification = Specification.where(null);
        if(!filter.isEmpty()){
            List<SearchCriteria> criterias = SearchCriteria.convertStringToCriteria(filter);
            List<GenericSpecification<Skill>> genericSpecifications = new ArrayList<>();
            criterias.forEach(criteria -> genericSpecifications.add(new GenericSpecification<>(criteria)));

            for(GenericSpecification genericSpecification : genericSpecifications){
                specification = specification.and(genericSpecification);
            }
        }
        
        Page<Skill> skillPage = this.skillRepository.findAll(specification, pageable);
        Meta meta = Meta.builder()
                        .current(skillPage.getNumber()+1)
                        .pageSize(skillPage.getSize())
                        .pages(skillPage.getTotalPages())
                        .total(skillPage.getTotalElements())
                        .build();
        List<SkillDTO.Response> list = skillPage.getContent().stream().map(skill -> this.skillMapper.toResponse(skill)).toList();
        ResultPagination<List<SkillDTO.Response>> resultPagination = ResultPagination.<List<SkillDTO.Response>>builder()
                                                            .result(list)
                                                            .meta(meta)
                                                            .build();
        return resultPagination;
    }

    @Transactional 
    public void handleDeleteSkill(Long id){
        Skill skill = this.skillRepository.findById(id).orElseThrow(IdInvalidException::new);
        this.jobSkillRepository.deleteBySkillId(id);
        this.skillRepository.delete(skill);
    }

    @Transactional 
    public SkillDTO.Response handleUpdateSkill(SkillDTO.UpdateRequest updateRequest){
        Skill skill = this.skillRepository.findById(updateRequest.id()).orElseThrow(IdInvalidException::new);
        if(!updateRequest.name().equals(skill.getName()) && this.skillRepository.existsByName(updateRequest.name())){
            throw new AlreadyExistsException("Skill name already exists");
        }
        this.skillMapper.update(updateRequest, skill);
        return this.skillMapper.toResponse(skill);
    }
    
}
