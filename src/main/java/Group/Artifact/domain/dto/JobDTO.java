package Group.Artifact.domain.dto;

import java.time.LocalDate;
import java.util.List;

import Group.Artifact.util.constant.LevelEnum;

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
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
        LocalDate startDay,
        LocalDate endDay,
        List<Long>skillIds,
        Long companyId
    ){}

    record UpdateRequest(
        Long id,
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
        LocalDate startDay,
        LocalDate endDay,
        List<Long>skillId,
        Long companyId
    ){}
}
