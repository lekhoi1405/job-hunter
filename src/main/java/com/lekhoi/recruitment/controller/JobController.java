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

import com.lekhoi.recruitment.domain.dto.JobDTO;
import com.lekhoi.recruitment.service.JobService;
import com.lekhoi.recruitment.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @ApiMessage("Create Job")
    @PostMapping
    public ResponseEntity<JobDTO.Response> createJob(@RequestBody @Valid JobDTO.CreateRequest createRequest){
        return ResponseEntity.ok().body(this.jobService.handleCreateJob(createRequest));
    }

    @ApiMessage("Update Job")
    @PutMapping 
    public ResponseEntity<JobDTO.Response> UpdateJob(@RequestBody @Valid JobDTO.UpdateRequest updateRequest){
        return ResponseEntity.ok().body(this.jobService.handleUpdateJob(updateRequest));
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

    @ApiMessage("Delete Job")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long jobId){
        this.jobService.handleDeleteJob(jobId);
        return ResponseEntity.ok().body(null);
    } 
}
