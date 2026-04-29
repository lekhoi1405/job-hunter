package Group.Artifact.domain.dto.request.company;

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
}
