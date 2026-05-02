package Group.Artifact.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.request.company.CompanyCreateRequest;
import Group.Artifact.domain.dto.request.company.CompanyUpdateRequest;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.company.CompanyResponse;
import Group.Artifact.service.CompanyService;
import jakarta.validation.Valid;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyCreateRequest companyCreateRequest){
        return ResponseEntity.ok(this.companyService.handleCreateCompany(companyCreateRequest));
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultPagination<List<CompanyResponse>>> getAllCompanies(
                @RequestParam Optional<Integer> current, 
                @RequestParam Optional<Integer> pageSize){
        return ResponseEntity.ok(this.companyService.handleGetAllCompanies(current.orElse(1),pageSize.orElse(2)));
    }

    @GetMapping("/Companies/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(this.companyService.getCompanyById(id));
    }

    @PutMapping("/Companies")
    public ResponseEntity<CompanyResponse> updateCompany(@Valid @RequestBody CompanyUpdateRequest companyUpdateRequest) {
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(companyUpdateRequest));
    }

    @DeleteMapping("/Companies/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id){
        this.companyService.deleteCompany(id);
        return ResponseEntity.ok(null);
    }
    
}
