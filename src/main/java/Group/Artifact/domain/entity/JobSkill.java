package Group.Artifact.domain.entity;

import Group.Artifact.domain.base.AuditBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "JobSkills")
@Setter 
@Getter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class JobSkill extends AuditBaseEntity{
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")    
    private Job job;
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;
}
