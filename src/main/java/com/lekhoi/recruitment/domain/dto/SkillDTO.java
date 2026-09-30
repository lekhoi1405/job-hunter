package com.lekhoi.recruitment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface SkillDTO {
    record Response(
        Long id,
        String name
    ){}
    record CreateRequest(
        @NotBlank String name
    ){}
    record UpdateRequest(
        @NotNull Long id,
        @NotBlank String name
    ){}
}
