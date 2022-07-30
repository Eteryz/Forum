package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;

import java.util.List;

public interface ArticleService {

    void save(ArticleDTO articleDTO, String username);

    List<ArticleDTO> findAllArticle();

    String deleteArticle(String id);

    Article getOneById(String id);
}
