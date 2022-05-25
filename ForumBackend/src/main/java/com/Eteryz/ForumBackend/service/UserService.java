package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.entity.UserEntity;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.model.User;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(UserEntity userEntity) throws UserAlreadyExistException {
        if(userRepository.findUserByName(userEntity.getName()).isPresent()){
            throw new UserAlreadyExistException("A user with the same name already exists");
        }
        return User.toModel(userRepository.save(userEntity));
    }

    public List<User> findAllUsers(){
        return userRepository.findAll().stream()
                .map(User::toModel)
                .collect(Collectors.toList());
    }

    public User updateUser(User user){
        return  User.toModel(userRepository.save(user.toEntity()));
    }

    public User getOneUser(Long id){
        return User.toModel(userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User by id " + id + " was not found!")));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
