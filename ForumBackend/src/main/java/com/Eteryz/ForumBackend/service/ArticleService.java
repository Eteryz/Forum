package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.models.User;

import java.util.List;

public interface ArticleService {

    void save(ArticleDTO articleDTO, User user);

    List<ArticleDTO> findAllArticle();

    Long deleteArticle(Long id);
}
