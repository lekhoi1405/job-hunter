package Group.Artifact.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Group.Artifact.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    User findByEmail(String email);
    Page<User> findByNameContainingIgnoreCase (String name,Pageable pageable);
}
