package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface UserService {

    void addToFavorites(String username, Article article) throws FavoritesException, UserNotFoundException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(User user);

    User getOneUserById(String id) throws UserNotFoundException;

    User getOneUserByUsername(String username) throws UserNotFoundException;

    void deleteUser(String id) throws UserNotFoundException;

    void deleteArticleFromFavorites(String username, Article article) throws UserNotFoundException;

    List<ArticleDTO> getArticleIdFromFavorites(String username) throws UserNotFoundException;

    Set<String> addRoleToUser(String username, ERole role) throws UserRoleNotFoundException, UserNotFoundException;
}
