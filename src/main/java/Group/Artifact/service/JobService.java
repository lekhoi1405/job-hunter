package Group.Artifact.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

        this.validateDates(job, createRequest.startDay(), createRequest.endDay());

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

    public void handleCreateJobSkill(Set<Long> skillId, Job job){
         List<Skill> skills = this.validateSkills(skillId);
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

    public List<Skill> validateSkills(Set<Long> skillIds){
        List<Skill> skills = this.skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            Set<Long> missingSkills = new HashSet<>(skillIds);
            missingSkills.removeAll(skills.stream().map(s -> s.getId()).toList());
            throw new RuntimeException(
                "Skill ids not found " + missingSkills
            );
        }

        return skills;
    }

    private void validateDates(Job job, LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {

            throw new RuntimeException(
                "startDay must not be after endDay"
            );
        }
        job.setStartDay(start);
        job.setEndDay(end);
    }

    @Transactional 
    public JobDTO.Response handleUpdateJob(JobDTO.UpdateRequest updateRequest){
        Job job = this.jobRepository.findJobWithJobSKillById(updateRequest.id()).orElseThrow(() -> new IdInvalidException("Id can not be found!"));
        this.jobMapper.update(updateRequest, job);

        this.validateDates(job, updateRequest.startDay(), updateRequest.endDay());
        job.setStartDay(updateRequest.startDay());
        job.setEndDay(updateRequest.endDay());

        if(updateRequest.companyId()!=null && !updateRequest.companyId().equals(job.getCompany().getId())){
            Company company = this.companyRepository.findById(updateRequest.companyId()).orElseThrow(() -> new IdInvalidException("Id can not be found!"));
            job.setCompany(company);
        }
        Set<Long> newSkillId = new HashSet<>(this.validateSkills(updateRequest.skillIds()).stream().map(s -> s.getId()).toList());

        Set<Long> oldSkillId = new HashSet<>(job.getJobSkills().stream().map(js->js.getSkill().getId()).toList());

        Set<Long> skillToDelete = new HashSet<>(oldSkillId);
        Set<Long> skillToAdd = new HashSet<>(newSkillId);

        skillToDelete.removeAll(newSkillId);
        skillToAdd.removeAll(oldSkillId);

        if(!skillToDelete.isEmpty()){
            this.jobSkillRepository.deleteByJobIdAndSkillIds(job.getId(), skillToDelete);
        }

        List<JobSkill> jobSkills = skillToAdd.stream().map(id -> 
             JobSkill.builder()
                            .job(job)
                            .skill(this.skillRepository.getReferenceById(id))
                            .build()
        ).toList();
        this.jobSkillRepository.saveAll(jobSkills);

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
        this.jobRepository.delete(job);
    }
}
