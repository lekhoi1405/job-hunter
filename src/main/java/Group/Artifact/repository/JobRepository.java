package Group.Artifact.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import Group.Artifact.domain.entity.Job;

public interface JobRepository extends JpaRepository<Job,Long>, JpaSpecificationExecutor<Job>{
    Page<Job> findAll(Specification<Job> specification, Pageable pageable);
}
