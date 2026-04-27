package Group.Artifact.domain.base;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class AuditBaseEntity extends BaseEntity{
    @Column(updatable = false)
    // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss a",timezone = "GMT+7")
    private Instant createdAt;
    private Instant updatedAt;

    private String createdBy;
    private String updatedBy; 
}
