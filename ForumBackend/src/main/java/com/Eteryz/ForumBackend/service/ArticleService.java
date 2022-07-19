package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    void save(ArticleDTO articleDTO);

    List<ArticleDTO> findAllArticle();

    Long deleteArticle(Long id);
}
