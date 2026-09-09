package Group.Artifact.domain.dto;

import java.time.Instant;

import Group.Artifact.util.constant.GenderEnum;

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
        String name, 
        String email, 
        String password, 
        Integer age, 
        GenderEnum gender,
        Long companyId,  
        String address){}
    record CreateResponse(
        Long id,
        String name,
        String email,
        String address,
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
        Integer age,
        Long companyId,  
        GenderEnum gender){}
    record UpdateResponse(
        Long id,
        String name,
        String address,
        Integer age,
        GenderEnum gender,
        Long companyId,  
        Instant updatedAt,
        String updatedBy){}

}
