package Group.Artifact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.company.CompanyCreateRequest;
import Group.Artifact.domain.dto.request.company.CompanyUpdateRequest;
import Group.Artifact.domain.dto.response.company.CompanyCreateResponse;
import Group.Artifact.domain.dto.response.company.CompanyGetResponse;
import Group.Artifact.domain.dto.response.company.CompanyUpdateResponse;
import Group.Artifact.service.CompanyService;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.validation.Valid;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyCreateResponse> createCompany(@Valid @RequestBody CompanyCreateRequest companyCreateRequest){
        return ResponseEntity.ok().body(this.companyService.handleCreateCompany(companyCreateRequest));
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyGetResponse>> getAllCompanies(){
        return ResponseEntity.ok().body(this.companyService.handleGetAllCompanies());
    }

    @GetMapping("/Companies/{id}")
    public ResponseEntity<CompanyGetResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.companyService.getCompanyById(id));
    }

    @PutMapping("/Companies")
    public ResponseEntity<CompanyUpdateResponse> updateCompany(@Valid @RequestBody CompanyUpdateRequest companyUpdateRequest) {
        return ResponseEntity.ok().body(this.companyService.handleUpdateCompany(companyUpdateRequest));
    }

    @DeleteMapping("/Companies/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id){
        this.companyService.deleteCompanyByid(id);
        return ResponseEntity.ok().body(null);
    }

}
