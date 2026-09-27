package Group.Artifact.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.JobDTO;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.ResultPagination.Meta;
import Group.Artifact.domain.entity.Company; 
import Group.Artifact.domain.entity.Job;
import Group.Artifact.domain.entity.JobSkill;
import Group.Artifact.domain.entity.Skill;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.repository.CompanyRepository;
import Group.Artifact.repository.JobRepository;
import Group.Artifact.repository.JobSkillRepository;
import Group.Artifact.repository.SkillRepository;
import Group.Artifact.service.mapper.JobMapper;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class JobService {
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final JobSkillRepository jobSkillRepository;
    private final JobMapper jobMapper;

    @Transactional 
    public JobDTO.Response handleCreateJob(JobDTO.CreateRequest createRequest){
        Job job = this.jobMapper.toEntity(createRequest);
        if(createRequest.companyId()!=null){
            Company company = this.companyRepository.findById(createRequest.companyId()).orElseThrow(IdInvalidException::new);
            job.setCompany(company);
        }
        job.setActive(true);
        job = this.jobRepository.save(job);
        if(createRequest.skillIds()!=null&&!createRequest.skillIds().isEmpty()){
           this.handleCreateJobSkill(createRequest.skillIds(), job);
        }
        return this.jobMapper.toResponse(job);
    }

    public void handleCreateJobSkill(List<Long> skillId, Job job){
         List<Skill> skills = this.skillRepository.findAllById(skillId);
            List<JobSkill> jobSkills = new ArrayList<>();
            for(Skill skill : skills){
                JobSkill jobSkill = JobSkill.builder()
                                            .job(job)
                                            .skill(skill)
                                            .build();
                jobSkills.add(jobSkill);
            }
            this.jobSkillRepository.saveAll(jobSkills);
    }

    @Transactional 
    public JobDTO.Response handleUpdateJob(JobDTO.UpdateRequest updateRequest){
        Job job = this.jobRepository.findJobWithJobSKillById(updateRequest.id()).orElseThrow(IdInvalidException::new);
        this.jobMapper.update(updateRequest, job);
        if(updateRequest.companyId()!=null){
            Company company = this.companyRepository.findById(updateRequest.companyId()).orElseThrow(IdInvalidException::new);
            job.setCompany(company);
        }
        Set<Long> newSkillId = new HashSet<>(this.skillRepository.findAllById(updateRequest.skillId())
                                                                    .stream().map(s -> s.getId()).toList());

        Set<Long> oldSkillId = new HashSet<>(job.getJobSkills().stream().map(js->js.getSkill().getId()).toList());

        Set<Long> skillToDelete = new HashSet<>(oldSkillId);
        Set<Long> skillToAdd = new HashSet<>(newSkillId);

        skillToDelete.removeAll(newSkillId);
        skillToAdd.removeAll(oldSkillId);

        for(Long id : skillToDelete){
            this.jobSkillRepository.deleteByJobIdAndSkillId(job.getId(), id);
        }
        for(Long id : skillToAdd){
            JobSkill jobSkill = JobSkill.builder()
                                        .job(job)
                                        .skill(this.skillRepository.getReferenceById(id))
                                        .build();
            this.jobSkillRepository.save(jobSkill);
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
        
        Page<Job> jobPage = this.jobRepository.findAll(specification, pageable);
        Meta meta = Meta.builder()
                        .current(jobPage.getNumber()+1)
                        .pageSize(jobPage.getSize())
                        .pages(jobPage.getTotalPages())
                        .total(jobPage.getTotalElements())
                        .build();
        List<JobDTO.Response> list = jobPage.getContent().stream().map(job -> this.jobMapper.toResponse(job)).toList();
        ResultPagination<List<JobDTO.Response>> resultPagination = ResultPagination.<List<JobDTO.Response>>builder()
                                                            .result(list)
                                                            .meta(meta)
                                                            .build();
        return resultPagination;
    }

    @Transactional 
    public void handleDeleteJob(Long jobId){
        Job job = this.jobRepository.findById(jobId).orElseThrow(IdInvalidException::new);
        this.jobSkillRepository.deleteByJobId(job.getId());
        this.jobRepository.deleteById(job.getId());
    }
}
