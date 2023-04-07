package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.entity.Article;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.PermissionException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;

import java.util.List;


public interface ArticleService {

    ArticleDTO save(ArticleDTO articleDTO, String username) throws UserNotFoundException;

    List<ArticleDTO> findAllArticle();

    List<ArticleDTO> findAllArticlesByAuthor(String username) throws UserNotFoundException;

    boolean deleteArticle(String articleId, String username) throws PermissionException, ArticleNotFoundException, UserNotFoundException;

    ArticleDTO getOneById(String id) throws ArticleNotFoundException;

    Article getArticleById(String id) throws ArticleNotFoundException;

    void addToFavorites(String username, String articleId) throws FavoritesException, UserNotFoundException, ArticleNotFoundException;

    void deleteArticleFromFavorites(String username, String articleId) throws UserNotFoundException, ArticleNotFoundException;

    List<ArticleDTO> getArticleIdFromFavorites(String username) throws UserNotFoundException;

}
