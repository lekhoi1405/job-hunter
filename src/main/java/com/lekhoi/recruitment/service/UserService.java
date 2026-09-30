package com.lekhoi.recruitment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lekhoi.recruitment.domain.entity.Company;
import com.lekhoi.recruitment.domain.entity.User;
import com.lekhoi.recruitment.domain.specification.GenericSpecification;
import com.lekhoi.recruitment.domain.specification.SearchCriteria;
import com.lekhoi.recruitment.domain.dto.UserDTO;
import com.lekhoi.recruitment.domain.dto.response.ResultPagination;
import com.lekhoi.recruitment.domain.dto.response.ResultPagination.Meta;
import com.lekhoi.recruitment.repository.CompanyRepository;
import com.lekhoi.recruitment.repository.UserRepository;
import com.lekhoi.recruitment.service.mapper.UserMapper;
import com.lekhoi.recruitment.util.error.ExceptionCustom.AlreadyExistsException;
import com.lekhoi.recruitment.util.error.ExceptionCustom.IdInvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CompanyRepository companyRepository;


    public UserDTO.CreateResponse handleCreateUser(UserDTO.CreateRequest createRequest){
        if(this.userRepository.existsByEmail(createRequest.email()))throw new AlreadyExistsException("email existed");
        User user = userMapper.toEntity(createRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(createRequest.companyId()!=null){
            Company company = this.companyRepository.findById(createRequest.companyId()).orElseThrow(IdInvalidException::new);
            user.setCompany(company);
        }
        return this.userMapper.toCreateResponse(this.userRepository.save(user));
    }

    public UserDTO.Response handleFindUserById(long id){
        User user = this.userRepository.findById(id)
                                        .orElseThrow(IdInvalidException::new);
        return this.userMapper.toResponse(user);
    }

    public ResultPagination<List<UserDTO.Response>> handleFindAllUser(Integer current, Integer pageSize, String filter){
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);

        Specification<User> specification = Specification.where(null);


        if(!filter.trim().isEmpty()){
            List<SearchCriteria> criterias = SearchCriteria.convertStringToCriteria(filter);
            List<GenericSpecification<User>> genericSpecifications = new ArrayList<>();
            criterias.forEach(criteria -> genericSpecifications.add(new GenericSpecification<>(criteria)));

             for (GenericSpecification<User> genericSpecification : genericSpecifications) {
                specification = specification.and(genericSpecification);
            }
        }

        Page<User> UserPage = this.userRepository.findAll(specification, pageable);

        Meta meta = Meta.builder()
                        .current(UserPage.getNumber()+1)
                        .pageSize(UserPage.getSize())
                        .pages(UserPage.getTotalPages())
                        .total(UserPage.getTotalElements())
                        .build();

        List<UserDTO.Response> content = UserPage.getContent().stream().map(user -> this.userMapper.toResponse(user)).toList();

        return ResultPagination.<List<UserDTO.Response>> builder()
                                                            .meta(meta)
                                                            .result(content)
                                                            .build();
    }
    
    @Transactional
    public void handleDeleteUser(long id){
        User user = this.userRepository.findById(id).orElseThrow(IdInvalidException::new);
        this.refreshTokenService.handleDeleteByUserId(user.getId());
        this.userRepository.deleteById(user.getId());
    }

    @Transactional
    public UserDTO.UpdateResponse handleUpdateUser(UserDTO.UpdateRequest updateRequest){
        User user = this.userRepository.findById(updateRequest.id()).orElseThrow(IdInvalidException::new);

        Long companyId = Optional.ofNullable(user.getCompany()).map(Company::getId).orElse(null);

        if(updateRequest.companyId()!=null){
            Company company = this.companyRepository.findById(updateRequest.companyId()).orElseThrow(() -> new IdInvalidException("Id can not be found!"));
            if(companyId!=null){
                if(!updateRequest.companyId().equals(companyId)){
                    user.setCompany(company);
                }
            }
            else{
                user.setCompany(company);
            }
        }

        this.userMapper.update(updateRequest, user);
        
        return this.userMapper.toUpdateResponse(user);
    }

    public User handleGetUserByUsername(String username){
        return this.userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }

    public User handleGetUserProxyById(Long id){
        return this.userRepository.getReferenceById(id);
    }
}
