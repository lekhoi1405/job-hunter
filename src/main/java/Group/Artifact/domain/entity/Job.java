package Group.Artifact.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Group.Artifact.domain.base.AuditBaseEntity;
import Group.Artifact.util.constant.LevelEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private String name;
    private String location;
    private Integer quantity;
    private LevelEnum level;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private LocalDate startDay;
    private LocalDate endDay;
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Builder.Default
    @OneToMany(mappedBy = "job")
    private List<JobSkill> jobSkills = new ArrayList<>();
}
