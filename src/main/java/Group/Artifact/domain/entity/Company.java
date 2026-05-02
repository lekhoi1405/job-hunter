package Group.Artifact.domain.entity;

import Group.Artifact.domain.base.AuditBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company extends AuditBaseEntity{
    private String name;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String description; 

    private String address;
    private String logo;
}
