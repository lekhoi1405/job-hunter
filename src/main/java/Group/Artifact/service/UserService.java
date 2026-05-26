package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.entity.User;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.domain.dto.request.user.UserCreateRequest;
import Group.Artifact.domain.dto.request.user.UserUpdateRequest;
import Group.Artifact.domain.dto.response.Meta;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.user.UserCreateResponse;
import Group.Artifact.domain.dto.response.user.UserResponse;
import Group.Artifact.domain.dto.response.user.UserUpdateResponse;
import Group.Artifact.repository.UserRepository;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCreateResponse handleCreateUser(UserCreateRequest createRequest){
        if(this.userRepository.existsByEmail(createRequest.getEmail()))throw new IdInvalidException("email existed");
        User user = UserCreateRequest.toEntity(createRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserCreateResponse.fromEntity(this.userRepository.save(user));  
    }

    public void handleDeleteUser(long id){
        if(id > 1500)throw new IdInvalidException("khong lon hon 1500");
        this.userRepository.deleteById(id);
    }

    public ResultPagination<List<UserCreateResponse>> handleFindAllUser(Integer current, Integer pageSize, String filter){
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);

        Specification<User> specification = Specification.where(null);


        if(!filter.trim().isEmpty()){
            List<SearchCriteria> criterias = SearchCriteria.convertStringToCriteria(filter);
            List<GenericSpecification<SearchCriteria>> genericSpecifications = new ArrayList<>();
            criterias.forEach(criteria -> genericSpecifications.add(new GenericSpecification<>(criteria)));

             for (GenericSpecification genericSpecification : genericSpecifications) {
                specification = specification.and(genericSpecification);
            }
        }

        Page<User> page = this.userRepository.findAll(specification, pageable);

        Meta meta = Meta.builder()
                        .current(page.getNumber()+1)
                        .pageSize(page.getSize())
                        .pages(page.getTotalPages())
                        .total(page.getTotalElements())
                        .build();

        List<UserCreateResponse> content = page.getContent().stream().map(UserCreateResponse::fromEntity).toList();

        return ResultPagination.<List<UserCreateResponse>>builder()
                                                    .meta(meta)
                                                    .Result(content)
                                                    .build();

    }

    public UserResponse handleFindUserById(long id){
        User user = this.userRepository.findById(id)
                                        .orElseThrow(IdInvalidException::new);
        return UserResponse.fromEntity(user);
    }

    @Transactional
    public UserUpdateResponse handleUpdateUser(UserUpdateRequest userUpdateRequest){
        User current = this.userRepository.findById(userUpdateRequest.getId()).orElseThrow(IdInvalidException::new);
        UserUpdateRequest.update(userUpdateRequest, current);
        return UserUpdateResponse.fromEntity(current);
    }

    public User handleGetUserByUsername(String username){
        return this.userRepository.findByEmail(username);
    }
}
