package com.lekhoi.recruitment.domain.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.lekhoi.recruitment.util.constant.LevelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface JobDTO {
    record Response(
        Long id,
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
        Long companyId,
        LocalDate startDay,
        LocalDate endDay
    ){}

    record CreateRequest(
        @NotBlank 
        String name,
        String location,

        @Positive 
        Integer quantity,

        @NotNull 
        LevelEnum level,

        @NotBlank 
        String description,

        @NotNull 
        LocalDate startDay,

        @NotNull 
        LocalDate endDay,
        Set<Long>skillIds,
        Long companyId
    ){}

    record UpdateRequest(
        @NotNull 
        Long id,
        
        @NotBlank 
        String name,

        @NotBlank 
        String location,

        @Positive 
        Integer quantity,

        @NotNull 
        LevelEnum level,

        @NotBlank 
        String description,
        
        @NotNull 
        LocalDate startDay,

        @NotNull 
        LocalDate endDay,

        @NotNull 
        Set<Long>skillIds,
        Long companyId
    ){}
}
