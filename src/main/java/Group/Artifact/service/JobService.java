package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.JobDTO;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.ResultPagination.Meta;
import Group.Artifact.domain.entity.Job;
import Group.Artifact.domain.entity.JobSkill;
import Group.Artifact.domain.entity.Skill;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.repository.JobRepository;
import Group.Artifact.repository.JobSKillRepository;
import Group.Artifact.repository.SkillRepository;
import Group.Artifact.service.mapper.JobMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class JobService {
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final JobSKillRepository jobSKillRepository;
    private final JobMapper jobMapper;
    
    public JobDTO.Response handleCreateJob(JobDTO.CreateRequest createRequest){
        Job job = this.jobMapper.toEntity(createRequest);
        job.setActive(true);
        return this.jobMapper.toResponse(this.jobRepository.save(job));
    }
    @Transactional 
    public JobDTO.Response handleCreateJobWithSkill(JobDTO.CreateWithSkillRequest createRequest){
        Job job = this.jobRepository.save(this.jobMapper.toEntity(createRequest));
        if(createRequest.SkillId()!=null&&!createRequest.SkillId().isEmpty()){
            List<Skill> skills = this.skillRepository.findAllById(createRequest.SkillId());
            List<JobSkill> jobSkills = new ArrayList<>();
            for(Skill skill : skills){
                JobSkill jobSkill = JobSkill.builder()
                                            .job(job)
                                            .skill(skill)
                                            .build();
                jobSkills.add(jobSkill);
            }
            this.jobSKillRepository.saveAll(jobSkills);
        }
       
        return this.jobMapper.toResponse(job);
    }

    public ResultPagination<List<JobDTO.Response>> handleGetAllJob(Integer current, Integer pageSize, String filterRequest){
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);
        String filter = filterRequest.trim();
        Specification<Job> specification = Specification.where(null);
        if(!filter.isEmpty()){
            List<SearchCriteria> criterias = SearchCriteria.convertStringToCriteria(filter);
            List<GenericSpecification<Job>> genericSpecifications = new ArrayList<>();
            criterias.forEach(criteria -> genericSpecifications.add(new GenericSpecification<>(criteria)));

            for(GenericSpecification genericSpecification : genericSpecifications){
                specification = specification.and(genericSpecification);
            }
        }
        
        Page<Job> JobPage = this.jobRepository.findAll(specification, pageable);
        Meta meta = Meta.builder()
                        .current(JobPage.getNumber()+1)
                        .pageSize(JobPage.getSize())
                        .pages(JobPage.getTotalPages())
                        .total(JobPage.getTotalElements())
                        .build();
        List<JobDTO.Response> list = JobPage.getContent().stream().map(job -> this.jobMapper.toResponse(job)).toList();
        ResultPagination<List<JobDTO.Response>> resultPagination = ResultPagination.<List<JobDTO.Response>>builder()
                                                            .result(list)
                                                            .meta(meta)
                                                            .build();
        return resultPagination;
    }
}
