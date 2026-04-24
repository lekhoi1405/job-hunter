package Group.Artifact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Group.Artifact.domain.Company;
@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    // Company save(Company company);
}
