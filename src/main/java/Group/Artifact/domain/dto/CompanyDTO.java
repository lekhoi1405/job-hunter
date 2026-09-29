package Group.Artifact.domain.dto;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface CompanyDTO {
    record CreateRequest(
        @NotBlank
        String name, 
        String description,

        @NotBlank
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
        @NotNull
        Long id,

        @NotBlank 
        String name,

        @NotBlank 
        String description,
        String address,
        String logo) {
    }
}
