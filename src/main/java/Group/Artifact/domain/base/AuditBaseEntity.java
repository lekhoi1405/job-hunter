package Group.Artifact.domain.base;

import java.time.Instant;

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
    private Instant createdAt;
    private Instant updatedAt;
}
