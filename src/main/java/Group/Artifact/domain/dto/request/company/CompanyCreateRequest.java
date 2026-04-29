package Group.Artifact.domain.dto.request.company;

import Group.Artifact.domain.Company;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyCreateRequest {
    @NotBlank(message = "name can not be blank")
    private String name;
    private String description;
    @NotBlank(message = "address can not be blank") 
    private String address;
    private String logo;
    public static Company toEntity(CompanyCreateRequest companyCreateRequest){
        return Company.builder()
                        .name(companyCreateRequest.getName())
                        .description(companyCreateRequest.getDescription())
                        .address(companyCreateRequest.getAddress())
                        .logo(companyCreateRequest.getLogo())
                        .build();         
    }
}
