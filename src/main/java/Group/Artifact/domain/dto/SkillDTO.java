package Group.Artifact.domain.dto;

public interface SkillDTO {
    record Response(
        String name
    ){}
    record CreateRequest(
        String name
    ){}
    record UpdateRequest(
        Long id,
        String name
    ){}
}
