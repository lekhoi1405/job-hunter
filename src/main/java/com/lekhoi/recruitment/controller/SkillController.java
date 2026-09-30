package com.lekhoi.recruitment.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lekhoi.recruitment.domain.dto.SkillDTO;
import com.lekhoi.recruitment.service.SkillService;
import com.lekhoi.recruitment.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/skills")
@RequiredArgsConstructor 
public class SkillController {
    private final SkillService skillService;

    @ApiMessage("Create Skill")
    @PostMapping 
    public ResponseEntity<SkillDTO.Response> createSkill(@RequestBody @Valid SkillDTO.CreateRequest createRequest){
        return ResponseEntity.ok().body(this.skillService.handleCreateSkill(createRequest));
    }

    @ApiMessage("Fetch All skills")
    @GetMapping 
    public ResponseEntity<Object> getAllskills(
        @RequestParam Optional<Integer> current,
        @RequestParam Optional<Integer> pageSize,
        @RequestParam Optional<String> filter
    ){
        return ResponseEntity.ok().body(this.skillService.handleGetAllskill(current.orElse(1), pageSize.orElse(2), filter.orElse("")));
    }

    @ApiMessage("Delete skill")
    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long skillId){
        this.skillService.handleDeleteSkill(skillId);
        return ResponseEntity.ok().body(null);
    } 

    @ApiMessage("Update skill")
    @PutMapping 
    public ResponseEntity<SkillDTO.Response> Updateskill(@RequestBody @Valid SkillDTO.UpdateRequest updateRequest){
        return ResponseEntity.ok().body(this.skillService.handleUpdateSkill(updateRequest));
    }
}
