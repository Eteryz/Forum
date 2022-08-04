package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface UserService {

    void addToFavorites(String username, Article article) throws FavoritesException;

    List<UserDTO> findAllUsers();

    UserDTO updateUser(User user);

    User getOneUserById(String id);

    User getOneUserByUsername(String username);

    Long deleteUser(Long id);

    void deleteArticleFromFavorites(String username, Article article);

    List<ArticleDTO> getArticleIdFromFavorites(String username);
}
