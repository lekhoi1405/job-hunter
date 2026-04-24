package Group.Artifact.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.CompanyCreateRequest;
import Group.Artifact.domain.dto.response.CompanyCreateResponse;
import Group.Artifact.service.CompanyService;
import jakarta.validation.Valid;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CompanyCreateRequest companyCreateRequest){
        Company companySaved = this.companyService.handleCreateCompany(companyCreateRequest);
        CompanyCreateResponse companyCreateResponse = CompanyCreateResponse.fromEntity(companySaved);
        return ResponseEntity.ok().body(companyCreateResponse);
    }
}
