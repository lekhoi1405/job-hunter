package Group.Artifact.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import Group.Artifact.domain.entity.User;

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
    void deleteById(int id);
    @Modifying
    @Query("UPDATE User u SET u.company = null where u.company.id = :companyId")
    void setNullByCompanyId(Long companyId);
}
