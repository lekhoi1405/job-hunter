package Group.Artifact.domain.dto.response;

import java.time.Instant;

import Group.Artifact.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyCreateResponse {
    private long id;
    private String name;
    private String description;
    private String address;
    private String logo;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    public static CompanyCreateResponse fromEntity(Company company){
        if(company==null)return null;

        return CompanyCreateResponse.builder()
                                    .id(company.getId())
                                    .name(company.getName())
                                    .description(company.getDescription())
                                    .address(company.getAddress())
                                    .logo(company.getLogo())
                                    .createdAt(company.getCreatedAt())
                                    .updatedAt(company.getUpdatedAt())
                                    .createdBy(company.getCreatedBy())
                                    .updatedBy(company.getUpdateBy())
                                    .build();
    }
}
