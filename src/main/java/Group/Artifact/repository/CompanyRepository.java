package Group.Artifact.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import Group.Artifact.domain.entity.Company;
public interface CompanyRepository extends JpaRepository<Company,Long>, JpaSpecificationExecutor<Company>{
    Page<Company> findAll(Specification<Company> specification, Pageable pageable);
    void deleteById(int id);
    
    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.jobs WHERE c.id = :companyId")
    Optional<Company> findByIdWithJob(Long companyId);
}
 