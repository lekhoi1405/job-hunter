package Group.Artifact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.SkillDTO;
import Group.Artifact.service.SkillService;
import Group.Artifact.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/skills")
@RequiredArgsConstructor 
public class SkillController {
    private final SkillService skillService;

    @ApiMessage("Create Job")
    @PostMapping 
    public ResponseEntity<SkillDTO.Response> createSkill(@RequestBody SkillDTO.CreateRequest createRequest){
        return ResponseEntity.ok().body(this.skillService.handleCreateSkill(createRequest));
    }
}
