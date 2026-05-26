package Group.Artifact.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.request.user.UserCreateRequest;
import Group.Artifact.domain.dto.request.user.UserUpdateRequest;
import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.user.UserCreateResponse;
import Group.Artifact.domain.dto.response.user.UserResponse;
import Group.Artifact.domain.dto.response.user.UserUpdateResponse;
import Group.Artifact.service.UserService;
import Group.Artifact.util.annotation.ApiMessage;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    
    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiMessage("Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable long id){
        return ResponseEntity.ok(this.userService.handleFindUserById(id));
    }

    @ApiMessage("Fetch all users")
    @GetMapping
    public ResponseEntity<ResultPagination<List<UserCreateResponse>>> getAllUser(
            @RequestParam Optional<Integer> current, 
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> filter){
        return ResponseEntity.ok(this.userService.handleFindAllUser(current.orElse(1),pageSize.orElse(2), filter.orElse("")));
    }   

    @ApiMessage("Create User")
    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseEntity.ok(this.userService.handleCreateUser(userCreateRequest));
    }

    @ApiMessage("Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.userService.handleDeleteUser(id);  
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @ApiMessage("Update user")
    @PutMapping()
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(this.userService.handleUpdateUser(userUpdateRequest));
    }
}
