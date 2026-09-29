package Group.Artifact.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Group.Artifact.domain.entity.Job;

public interface JobRepository extends JpaRepository<Job,Long>, JpaSpecificationExecutor<Job>{
    Page<Job> findAll(Specification<Job> specification, Pageable pageable);
    
    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.jobSkills WHERE j.id = :jobId")
    Optional<Job> findJobWithJobSKillById(Long jobId);

    @Modifying 
    @Query("DELETE FROM Job j WHERE j.company.id = :companyId")
    void deleteByCompanyId(Long companyId);
}
