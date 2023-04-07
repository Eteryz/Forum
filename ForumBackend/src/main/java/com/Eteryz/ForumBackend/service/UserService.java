package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.types.ERole;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<UserDTO> findAllDeleteOrExistsUsers(boolean isDeleted);

    UserDTO save(User user);

    List<UserDTO> findAllUsers();

    UserDTO updateUser(String username, UserDTO user) throws UserNotFoundException;

    UserDTO updateAvatarUser(String username, MultipartFile file) throws UserNotFoundException, IOException;

    UserDTO getOneUserByUsername(String username) throws UserNotFoundException;

    void deleteUserByUsername(String username) throws UserNotFoundException;

    boolean addRoleToUser(String username, ERole role) throws UserRoleNotFoundException, UserNotFoundException;

    void deleteProfileImage(String username) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

}
