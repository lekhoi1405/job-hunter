package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.dto.CompanyDTO;
import Group.Artifact.domain.dto.mapper.CompanyMapper;
import Group.Artifact.domain.dto.response.Meta;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.entity.Company;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.repository.CompanyRepository;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository,CompanyMapper companyMapper){
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    public CompanyDTO.Response handleCreateCompany(CompanyDTO.CreateRequest companyCreateRequest){
        Company company = this.companyMapper.toEntity(companyCreateRequest);
        this.companyRepository.save(company);
        return this.companyMapper.toResponse(company);
    } 

    public ResultPagination<List<CompanyDTO.Response>> handleGetAllCompanies(Integer current,Integer pageSize, String filterRequest){
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
        Page<Company>  companyPageable = this.companyRepository.findAll(specification, pageable);

        Meta meta = Meta.builder()
                        .current(companyPageable.getNumber()+1)
                        .pageSize(companyPageable.getSize())
                        .pages(companyPageable.getTotalPages())
                        .total(companyPageable.getTotalElements())
                        .build();
                                                        
        List<CompanyDTO.Response> content = companyPageable.getContent().stream()
                                                        .map(company -> this.companyMapper.toResponse(company))
                                                        .toList();

        return ResultPagination.<List<CompanyDTO.Response>>builder()
                                                        .meta(meta)
                                                        .Result(content)
                                                        .build();  
                                                                                                     
    }

    @Transactional
    public CompanyDTO.Response handleUpdateCompany(CompanyDTO.UpdateRequest companyUpdateRequest) {
        Company company = this.companyRepository.findById(companyUpdateRequest.id())
                                                .orElseThrow(IdInvalidException::new);

        this.companyMapper.update(companyUpdateRequest, company);
        
        return this.companyMapper.toResponse(company);
    }

    public CompanyDTO.Response handleGetCompanyById(Long id){
        return this.companyMapper.toResponse(this.companyRepository.findById(id)
                                                .orElseThrow(IdInvalidException::new));
    }

    public void handleDeleteCompanyById(Long id){
        this.companyRepository.delete(this.companyRepository.findById(id)
                                    .orElseThrow(IdInvalidException::new));
    }
}
