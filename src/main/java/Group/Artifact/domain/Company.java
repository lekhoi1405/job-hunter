package Group.Artifact.domain;

import java.time.Instant;

import Group.Artifact.domain.base.AuditBaseEntity;
import Group.Artifact.domain.dto.request.CompanyCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String createdBy;
    private String updatedBy;
    private String hihi;
    public static Company fromRequest(CompanyCreateRequest companyCreateRequest){
        return Company.builder()
                        .name(companyCreateRequest.getName())
                        .description(companyCreateRequest.getDescription())
                        .address(companyCreateRequest.getAddress())
                        .logo(companyCreateRequest.getLogo())
                        .build();         
    }
}
