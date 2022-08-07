package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ArticleService {

    void save(ArticleDTO articleDTO, User user);

    List<ArticleDTO> findAllArticle();

    List<ArticleDTO> findAllArticlesByAuthor(User user);

    void deleteArticle(String articleId, User user) throws ArticleNotFoundException;

    void deleteArticle(String articleId) throws ArticleNotFoundException;

    Article getOneById(String id) throws ArticleNotFoundException;
}
