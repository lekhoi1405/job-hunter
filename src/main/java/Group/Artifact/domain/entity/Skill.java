package Group.Artifact.domain.entity;

import java.util.ArrayList;
import java.util.List;

import Group.Artifact.domain.base.AuditBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity 
@Table(name = "skills")
@Setter 
@Getter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class Skill extends AuditBaseEntity{
    @Column(unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "skill")
    private List<JobSkill> jobSkills = new ArrayList<>();
}
