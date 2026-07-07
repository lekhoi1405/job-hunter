package Group.Artifact.domain.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface CompanyDTO {
    record CreateRequest(
        @NotBlank(message = "name can not be blank")
        String name, 
        String description,

        @NotBlank(message = "address can not be blank")
        String address, 
        String logo){
    }
    
    record Response( 
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

    record UpdateRequest(    
        @NotNull(message = "id can not be blank")
        Long id,
        String name,
        String description,
        String address,
        String logo) {
    }
}
