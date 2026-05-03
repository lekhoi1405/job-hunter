package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.GenericSpecification;
import Group.Artifact.domain.SearchCriteria;
import Group.Artifact.domain.dto.request.company.CompanyCreateRequest;
import Group.Artifact.domain.dto.request.company.CompanyUpdateRequest;
import Group.Artifact.domain.dto.response.Meta;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.company.CompanyResponse;
import Group.Artifact.domain.entity.Company;
import Group.Artifact.repository.CompanyRepository;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    
    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public CompanyResponse handleCreateCompany(CompanyCreateRequest companyCreateRequest){
        Company company = CompanyCreateRequest.toEntity(companyCreateRequest);
        this.companyRepository.save(company);
        return CompanyResponse.fromEntity(company);
    } 

    public ResultPagination<List<CompanyResponse>> handleGetAllCompanies(Integer current,Integer pageSize, String filterRequest){
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);
    
        Specification<Company> specification = Specification.where(null);

        String filter = filterRequest.trim();
        if(!filter.isEmpty()){
            List<SearchCriteria> criterias = SearchCriteria.convertStringToCriteria(filter);
            List<GenericSpecification<Company>> genericSpecifications = new ArrayList<>();
            criterias.forEach(criteria -> genericSpecifications.add(new GenericSpecification<>(criteria)));
            

            for(GenericSpecification<Company> genericSpecification : genericSpecifications){
                specification = specification.and(genericSpecification);
            }
        }
        Page<Company>  companyPageable= this.companyRepository.findAll(specification, pageable);

        Meta meta = Meta.builder()
                        .current(companyPageable.getNumber()+1)
                        .pageSize(companyPageable.getSize())
                        .pages(companyPageable.getTotalPages())
                        .total(companyPageable.getTotalElements())
                        .build();
                                                        
        List<CompanyResponse> content = companyPageable.getContent().stream()
                                                        .map(CompanyResponse::fromEntity)
                                                        .toList();

        return ResultPagination.<List<CompanyResponse>>builder()
                                                        .meta(meta)
                                                        .Result(content)
                                                        .build();  
                                                                                                     
    }

    @Transactional
    public CompanyResponse handleUpdateCompany(CompanyUpdateRequest companyUpdateRequest) {
        Company company = this.companyRepository.findById(companyUpdateRequest.getId())
                                                .orElseThrow(IdInvalidException::new);

        CompanyUpdateRequest.update(companyUpdateRequest, company);
        
        return CompanyResponse.fromEntity(company);
    }

    public CompanyResponse handleGetCompanyById(Long id){
        return CompanyResponse.fromEntity(this.companyRepository.findById(id)
                                                .orElseThrow(IdInvalidException::new));
    }

    public void handleDeleteCompanyById(Long id){
        this.companyRepository.delete(this.companyRepository.findById(id)
                                    .orElseThrow(IdInvalidException::new));
    }
}
