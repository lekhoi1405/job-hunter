package Group.Artifact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.entity.User;
import Group.Artifact.domain.specification.GenericSpecification;
import Group.Artifact.domain.specification.SearchCriteria;
import Group.Artifact.domain.dto.UserDTO;
import Group.Artifact.domain.dto.mapper.UserMapper;
import Group.Artifact.domain.dto.response.Meta;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.repository.UserRepository;
import Group.Artifact.util.error.IdInvalidException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserDTO.CreateResponse handleCreateUser(UserDTO.CreateRequest createRequest){
        if(this.userRepository.existsByEmail(createRequest.email()))throw new IdInvalidException("email existed");
        User user = userMapper.toEntity(createRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userMapper.toCreateResponse(this.userRepository.save(user));  
    }

    public UserDTO.Response handleFindUserById(long id){
        User user = this.userRepository.findById(id)
                                        .orElseThrow(IdInvalidException::new);
        return this.userMapper.toResponse(user);
    }

    public ResultPagination<List<UserDTO.Response>> handleFindAllUser(Integer current, Integer pageSize, String filter){
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

        List<UserDTO.Response> content = page.getContent().stream().map(user -> this.userMapper.toResponse(user)).toList();

        return ResultPagination.<List<UserDTO.Response>> builder()
                                                            .meta(meta)
                                                            .Result(content)
                                                            .build();
    }

    public void handleDeleteUser(long id){
        if(id > 1500)throw new IdInvalidException("khong lon hon 1500");
        this.userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO.UpdateResponse handleUpdateUser(UserDTO.UpdateRequest userUpdateRequest){
        User current = this.userRepository.findById(userUpdateRequest.id()).orElseThrow(IdInvalidException::new);
        this.userMapper.update(userUpdateRequest, current);
        return this.userMapper.toUpdateResponse(current);
    }

    public User handleGetUserByUsername(String username){
        return this.userRepository.findByEmail(username).orElseThrow(()-> new BadCredentialsException("username not found"));
    }
}
