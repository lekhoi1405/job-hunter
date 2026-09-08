package Group.Artifact.domain.entity;

import Group.Artifact.domain.base.AuditBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "jobs")
@Entity 
@Setter 
@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class Job extends AuditBaseEntity{
    private  String name;
}
