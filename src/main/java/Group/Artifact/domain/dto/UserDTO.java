package Group.Artifact.domain.dto;

import java.time.Instant;

import Group.Artifact.util.constant.GenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public interface UserDTO {
    record Response(
        Long id, 
        String name, 
        String email, 
        String address, 
        Integer age, 
        GenderEnum gender,
        Long companyId, 
        Instant createdAt, 
        String createdBy, 
        Instant updatedAt, 
        String updatedBy){}

    record CreateRequest(
        @NotBlank 
        String name,
        
        @Email 
        @NotBlank 
        String email, 

        @NotBlank 
        String password,
        
        @Positive 
        Integer age, 
        GenderEnum gender,
        Long companyId,  
        String address){}
    record CreateResponse(
        Long id,
        String name,
        String email,
        String address,
        
        @Positive 
        Integer age,
        GenderEnum gender,
        Long companyId,
        Instant createdAt,
        String createdBy){
        }

    record UpdateRequest(
        Long id,
        String name,
        String address,

        @Positive 
        Integer age,
        Long companyId,  
        GenderEnum gender){}
    record UpdateResponse(
        Long id,
        String name,
        String address,

        @Positive 
        Integer age,
        GenderEnum gender,
        Long companyId){}

}
