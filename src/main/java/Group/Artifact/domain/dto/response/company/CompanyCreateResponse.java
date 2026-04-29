package Group.Artifact.domain.dto.response.company;

import java.time.Instant;

import Group.Artifact.domain.Company;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyCreateResponse {
    private Long id;
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
                                    .updatedBy(company.getUpdatedBy())
                                    .build();
    }
}
