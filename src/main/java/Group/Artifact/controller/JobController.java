package Group.Artifact.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.JobDTO;
import Group.Artifact.service.JobService;
import Group.Artifact.util.annotation.ApiMessage;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @ApiMessage("Create Job")
    @PostMapping 
    public ResponseEntity<JobDTO.Response> createJob(@RequestBody JobDTO.CreateRequest createRequest){
        return ResponseEntity.ok().body(this.jobService.handleCreateJob(createRequest));
    }
    @ApiMessage("Create Job With Skill")
    @PostMapping("/skill")
    public ResponseEntity<JobDTO.Response> createJobWithSkill(@RequestBody JobDTO.CreateWithSkillRequest createRequest){
        return ResponseEntity.ok().body(this.jobService.handleCreateJobWithSkill(createRequest));
    }
    

    @ApiMessage("Fetch All jobs")
    @GetMapping 
    public ResponseEntity<Object> getAllJobs(
        @RequestParam Optional<Integer> current,
        @RequestParam Optional<Integer> pageSize,
        @RequestParam Optional<String> filter
    ){
        return ResponseEntity.ok().body(this.jobService.handleGetAllJob(current.orElse(1), pageSize.orElse(2), filter.orElse("")));
    }
}
