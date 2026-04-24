package Group.Artifact.domain.base;

import java.time.Instant;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditListener {
    @PrePersist
    public void onPrePersist(Object object){
        if(object instanceof AuditBaseEntity auditBaseEntity){
            Instant now = Instant.now();
            auditBaseEntity.setCreatedAt(now);
            auditBaseEntity.setUpdatedAt(now);
        }
    }

    @PreUpdate
    public void onPreUpdated(Object object){
        if(object instanceof AuditBaseEntity auditBaseEntity){
            auditBaseEntity.setUpdatedAt(Instant.now());
        }
    }
}
