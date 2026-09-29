package Group.Artifact.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Group.Artifact.domain.entity.JobSkill;

public interface JobSkillRepository extends JpaRepository<JobSkill, Long>{
    @Modifying 
    @Query("DELETE FROM JobSkill js WHERE js.job.id = :jobId and js.skill.id IN :skillIds")
    void deleteByJobIdAndSkillIds(Long jobId, Set<Long> skillIds);

    @Modifying 
    @Query("DELETE FROM JobSkill js WHERE js.job.id = :jobId")
    void deleteByJobId(Long jobId);

    @Modifying 
    @Query("DELETE FROM JobSkill js WHERE js.job.id IN (SELECT j.id FROM Job j WHERE j.company.id = :companyId)")
    void  deleteByCompanyId(Long companyId);

    @Modifying 
    @Query("DELETE FROM JobSkill js where js.skill.id = :skillId")
    void deleteBySkillId(Long skillId);
}
