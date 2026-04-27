package Group.Artifact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.CompanyCreateRequest;
import Group.Artifact.domain.dto.response.CompanyCreateResponse;
import Group.Artifact.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Company handleCreateCompany(CompanyCreateRequest companyCreateRequest){
        Company company = Company.fromRequest(companyCreateRequest);
        return this.companyRepository.save(company);
    }

    public List<CompanyCreateResponse> handleGetAllCompanies(){
        return this.companyRepository.findAll().stream()
                                    .map(company -> CompanyCreateResponse.builder()
                                                                        .id(company.getId())
                                                                        .name(company.getName())
                                                                        .description(company.getDescription())
                                                                        .address(company.getAddress())
                                                                        .logo(company.getLogo())
                                                                        .createdAt(company.getCreatedAt())
                                                                        .createdBy(company.getCreatedBy())
                                                                        .updatedAt(company.getUpdatedAt())
                                                                        .updatedBy(company.getUpdatedBy())
                                                                        .build())
                                                                        .toList();
    }
    
}
