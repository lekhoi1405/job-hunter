package com.lekhoi.recruitment.domain.entity;

import com.lekhoi.recruitment.domain.base.AuditBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "JobSkills",
        uniqueConstraints = {
        @UniqueConstraint(
            name = "unique",
            columnNames = {"job_id", "skill_id"}
        )
    }
)
@Setter 
@Getter 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class JobSkill extends AuditBaseEntity{
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)    
    private Job job;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
