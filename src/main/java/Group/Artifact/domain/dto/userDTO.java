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
        String address){}
    record CreateResponse(
        Long id,
        String name,
        String email,
        String address,
        Integer age,
        GenderEnum gender,
        Instant createdAt,
        String createdBy){}

    record UpdateRequest(
        Long id,
        String name,
        String address,
        Integer age,
        GenderEnum gender){}
    record UpdateResponse(
        Long id,
        String name,
        String address,
        Integer age,
        GenderEnum gender,
        Instant updatedAt,
        String updatedBy){}

}
