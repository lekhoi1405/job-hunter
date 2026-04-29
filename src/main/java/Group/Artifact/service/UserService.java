package Group.Artifact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Group.Artifact.domain.User;
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

    public List<User> handleFindAllUser(){
        return this.userRepository.findAll();
    }

    public User handleFindUserById(long id){
        User user = this.userRepository.findById(id)
                                        .orElseThrow(()->new IdInvalidException("user not found"));
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
