package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void save(User user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getName()).isPresent())
            throw new UserAlreadyExistException("A user with the same name already exists");
        else
            userRepository.save(user);
    }

    @Override
    public void addToFavorites(String username, Article article) {
        User user = getOneUserByUsername(username);
        System.out.println(username);
        System.out.println(article);
        user.getFavorites().add(article);
        System.out.println(user.getFavorites().toString());
        userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(User user) {
        return UserDTO.toModel(userRepository.save(user));
    }

    @Override
    public User getOneUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found!"));
    }

    @Override
    public User getOneUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User by username " + username + " was not found!"));
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
