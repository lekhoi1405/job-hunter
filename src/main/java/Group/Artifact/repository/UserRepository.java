package Group.Artifact.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Group.Artifact.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>{
    User findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
