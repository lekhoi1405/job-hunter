package Group.Artifact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.company.CompanyCreateRequest;
import Group.Artifact.domain.dto.request.company.CompanyUpdateRequest;
import Group.Artifact.domain.dto.response.company.CompanyCreateResponse;
import Group.Artifact.domain.dto.response.company.CompanyGetResponse;
import Group.Artifact.domain.dto.response.company.CompanyUpdateResponse;
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

    public CompanyCreateResponse handleCreateCompany(CompanyCreateRequest companyCreateRequest){
        Company company = CompanyCreateRequest.toEntity(companyCreateRequest);
        this.companyRepository.save(company);
        return CompanyCreateResponse.fromEntity(company);
    }

    public List<CompanyGetResponse> handleGetAllCompanies(){
        return this.companyRepository.findAll().stream()
                                    .map(company -> CompanyGetResponse.fromEntity(company))
                                    .toList();
    }

    @Transactional
    public CompanyUpdateResponse handleUpdateCompany(CompanyUpdateRequest companyUpdateRequest) {
        Company company = this.companyRepository.findById(companyUpdateRequest.getId())
                                                .orElseThrow(()->new IdInvalidException("id not found"));

        if(companyUpdateRequest.getDescription()!=null)company.setDescription(companyUpdateRequest.getDescription());
        if(companyUpdateRequest.getLogo()!=null)company.setLogo(companyUpdateRequest.getLogo());
        if(companyUpdateRequest.getName()!=null)company.setName(companyUpdateRequest.getName());
        if(companyUpdateRequest.getAddress()!=null)company.setAddress(companyUpdateRequest.getAddress());
        
        return CompanyUpdateResponse.fromEntity(company);
    }

    public CompanyGetResponse getCompanyById(Long id){
        return CompanyGetResponse.fromEntity(this.companyRepository.findById(id)
                                                .orElseThrow(()->new IdInvalidException("Id not found")));
    }

    public void deleteCompanyByid(Long id){
        this.companyRepository.deleteById(id);
    }
}
