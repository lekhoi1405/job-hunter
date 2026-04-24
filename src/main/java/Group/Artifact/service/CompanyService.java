package Group.Artifact.service;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.Company;
import Group.Artifact.domain.dto.request.CompanyCreateRequest;
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
    
}
