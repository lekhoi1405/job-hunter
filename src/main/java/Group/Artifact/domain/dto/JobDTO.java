package Group.Artifact.domain.dto;

import java.time.LocalDate;
import java.util.List;

import Group.Artifact.util.constant.LevelEnum;

public interface JobDTO {
    record Response(
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
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
        LocalDate endDay
    ){}

    record CreateWithSkillRequest(
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
        LocalDate startDay,
        LocalDate endDay,
        List<Long>SkillId
    ){}
        record CreateWithSkillResponse(
        String name,
        String location,
        Integer quantity,
        LevelEnum level,
        String description,
        LocalDate startDay,
        LocalDate endDay,
        List<Long>SkillId
    ){}
}
