package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.models.User;

import java.util.List;

public interface UserService {

    void save(User user) throws UserAlreadyExistException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(User user);

    User getOneUserById(String id);

    User getOneUserByUsername(String username);

    Long deleteUser(Long id);
}
