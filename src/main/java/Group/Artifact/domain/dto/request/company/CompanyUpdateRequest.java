package Group.Artifact.domain.dto.request.company;

import Group.Artifact.domain.Company;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyUpdateRequest {
    @NotNull(message = "id can not be blank")
    private Long id;
    private String name;
    private String description;
    private String address;
    private String logo;

    public static void update(CompanyUpdateRequest companyUpdateRequest, Company company){
        if(companyUpdateRequest.getDescription()!=null)company.setDescription(companyUpdateRequest.getDescription());
        if(companyUpdateRequest.getLogo()!=null)company.setLogo(companyUpdateRequest.getLogo());
        if(companyUpdateRequest.getName()!=null)company.setName(companyUpdateRequest.getName());
        if(companyUpdateRequest.getAddress()!=null)company.setAddress(companyUpdateRequest.getAddress());
    }
}
