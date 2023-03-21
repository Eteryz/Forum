package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserDTO> findAllUsers();

    UserDTO updateUser(String username, UserDTO user) throws UserNotFoundException;

    UserDTO updateAvatarUser(String username, MultipartFile file) throws UserNotFoundException, IOException;

    UserDTO getOneUserByUsername(String username) throws UserNotFoundException;

    void deleteUserByUsername(String username) throws UserNotFoundException;

    void addRoleToUser(String username, ERole role) throws UserRoleNotFoundException, UserNotFoundException;

    void deleteProfileImage(String username) throws UserNotFoundException;
}
