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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.CompanyDTO;

import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.service.CompanyService;
import Group.Artifact.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    
    @ApiMessage("Create company")
    @PostMapping
    public ResponseEntity<CompanyDTO.Response> createCompany(@Valid @RequestBody CompanyDTO.CreateRequest companyCreateRequest){
        return ResponseEntity.ok(this.companyService.handleCreateCompany(companyCreateRequest));
    }

    @ApiMessage("Fetch all companies")
    @GetMapping
    public ResponseEntity<ResultPagination<List<CompanyDTO.Response>>> getAllCompanies(
                @RequestParam Optional<Integer> current, 
                @RequestParam Optional<Integer> pageSize,
                @RequestParam Optional<String> filter){
        return ResponseEntity.ok(this.companyService.handleGetAllCompanies(current.orElse(1),pageSize.orElse(2),filter.orElse("")));
    }

    @ApiMessage("Get company by id")
    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO.Response> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(this.companyService.handleGetCompanyById(id));
    }
    
    @ApiMessage("Update company")
    @PutMapping
    public ResponseEntity<CompanyDTO.Response> updateCompany(@Valid @RequestBody CompanyDTO.UpdateRequest companyUpdateRequest){
        return ResponseEntity.ok(this.companyService.handleUpdateCompany(companyUpdateRequest));
    }

    @ApiMessage("Delete company")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id){
        this.companyService.handleDeleteCompanyById(id);
        return ResponseEntity.ok(null);
    }

}
