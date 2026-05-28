package Group.Artifact.domain.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;

public interface CompanyDTO {
    record CompanyCreateRequest(
        @NotBlank(message = "name can not be blank")
        String name, 
        String description,

        @NotBlank(message = "address can not be blank")
        String address, 
        String logo){
    }
    
    public record CompanyResponse( 
        Long id,
        String name,
        String description,
        String address,
        String logo,
        Instant createdAt,
        Instant updatedAt,
        String createdBy,
        String updatedBy) {
    }
}
