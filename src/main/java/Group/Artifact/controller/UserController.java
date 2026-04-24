package Group.Artifact.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Group.Artifact.domain.User;
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
    public ResponseEntity<?> findUserById(@PathVariable long id)throws Exception{
        User userFound =  this.userService.handleFindUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userFound);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> lists = this.userService.handleFindAllUser();
        return ResponseEntity.ok(lists);
        // return ResponseEntity.status(HttpStatus.OK).body(lists);
    }   

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = this.userService.handleSaveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws IdInvalidException{
        if(id > 1500){              
            throw new IdInvalidException("khong lon hon 1500");
        }

        this.userService.handleDeleteUser(id);  
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user)throws Exception{
        User userUpdated = this.userService.handleUpdateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
