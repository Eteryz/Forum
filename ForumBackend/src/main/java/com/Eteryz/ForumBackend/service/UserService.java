package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.dto.UserDTO;

import java.util.List;

public interface UserService {

    void save(User user) throws UserAlreadyExistException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(UserDTO userDTO);

    UserDTO getOneUser(Long id);

    Long deleteUser(Long id);
}
