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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.dto.response.ResultPagination;
import Group.Artifact.domain.dto.response.user.UserResponse;
import Group.Artifact.domain.entity.User;
import Group.Artifact.service.UserService;
import Group.Artifact.util.error.IdInvalidException;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserService userService,PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getHomePage(){
        return ResponseEntity.status(HttpStatus.OK).body("home page");
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUserById(@PathVariable long id){
        return ResponseEntity.ok(this.userService.handleFindUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<ResultPagination<List<UserResponse>>> getAllUser(
            @RequestParam Optional<Integer> current, 
            @RequestParam Optional<Integer> pageSize,
            @RequestParam Optional<String> key){
        return ResponseEntity.ok(this.userService.handleFindAllUser(current.orElse(1),pageSize.orElse(2), key.orElse("")));
    }   

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(this.userService.handleSaveUser(user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        if(id > 1500){              
            throw new IdInvalidException("khong lon hon 1500");
        }

        this.userService.handleDeleteUser(id);  
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        return ResponseEntity.ok(this.userService.handleUpdateUser(user));
    }
}
