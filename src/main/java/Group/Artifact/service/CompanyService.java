package Group.Artifact.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.company.CompanyCreateRequest;
import Group.Artifact.domain.dto.request.company.CompanyUpdateRequest;
import Group.Artifact.domain.dto.response.company.CompanyResponse;
import Group.Artifact.repository.CompanyRepository;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {
    private final Group.Artifact.ArtifactApplication artifactApplication;
    private final CompanyRepository companyRepository;
    
    public CompanyService(CompanyRepository companyRepository, Group.Artifact.ArtifactApplication artifactApplication){
        this.companyRepository = companyRepository;
        this.artifactApplication = artifactApplication;
    }

    public CompanyResponse handleCreateCompany(CompanyCreateRequest companyCreateRequest){
        Company company = CompanyCreateRequest.toEntity(companyCreateRequest);
        this.companyRepository.save(company);
        return CompanyResponse.fromEntity(company);
    }

    public List<CompanyResponse> handleGetAllCompanies(Integer currentPage, String pageSize){

        // int current = Integer.parseInt(currentPage);
        int size = Integer.parseInt(pageSize);
        Sort sort = Sort.by("name").descending();
        Pageable pageable = PageRequest.of(currentPage, size, sort);

        return this.companyRepository.findAll(pageable).stream()
                                    .map(company -> CompanyResponse.fromEntity(company))
                                    .toList();
    }

    @Transactional
    public CompanyResponse handleUpdateCompany(CompanyUpdateRequest companyUpdateRequest) {
        Company company = this.companyRepository.findById(companyUpdateRequest.getId())
                                                .orElseThrow(()->new IdInvalidException("id not found"));

        CompanyUpdateRequest.update(companyUpdateRequest, company);
        
        return CompanyResponse.fromEntity(company);
    }

    public CompanyResponse getCompanyById(Long id){
        return CompanyResponse.fromEntity(this.companyRepository.findById(id)
                                                .orElseThrow(()->new IdInvalidException("Id not found")));
    }

    public void deleteCompany(Long id){
        this.companyRepository.delete(this.companyRepository.findById(id)
                                    .orElseThrow(()-> new IdInvalidException("Id not found")));
    }
}
