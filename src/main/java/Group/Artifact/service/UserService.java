package Group.Artifact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Group.Artifact.domain.entity.User;
import Group.Artifact.domain.dto.response.Meta;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.company.CompanyResponse;
import Group.Artifact.domain.dto.response.user.UserResponse;
import Group.Artifact.repository.UserRepository;
import Group.Artifact.util.error.IdInvalidException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User handleSaveUser(User user){
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(long id){
        this.userRepository.deleteById(id);
    }

    public ResultPagination<List<UserResponse>> handleFindAllUser(Integer current, Integer pageSize, String key){
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(current-1, pageSize, sort);

        Page<User> page = this.userRepository.findByNameContainingIgnoreCase(key,pageable);

        Meta meta = Meta.builder()
                        .current(page.getNumber()+1)
                        .pageSize(page.getSize())
                        .pages(page.getTotalPages())
                        .total(page.getTotalElements())
                        .build();

        List<UserResponse> content = page.getContent().stream().map(UserResponse::fromEntity).toList();

        return ResultPagination.<List<UserResponse>>builder()
                                                    .meta(meta)
                                                    .Result(content)
                                                    .build();

    }

    public User handleFindUserById(long id){
        User user = this.userRepository.findById(id)
                                        .orElseThrow(IdInvalidException::new);
        return user ;
    }

    public User handleUpdateUser(User user){
        User current = this.handleFindUserById(user.getId());
        if(current!=null){
            current.setEmail(user.getEmail());
            current.setName(user.getName());
            current.setPassword(user.getPassword());
            this.handleSaveUser(current);
        }
        return current;
    }

    public User handleGetUserByUsername(String username){
        return this.userRepository.findByEmail(username);
    }
}
