package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.model.Article;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void addArticle(Article article){
        articleRepository.save(article.toEntity());
    }

    public List<Article> findAllArticle(){
        return articleRepository.findAll().stream()
                .map(Article::toModel)
                .collect(Collectors.toList());
    }

    public Long deleteArticle(Long id){
        articleRepository.deleteById(id);
        return id;
    }
}
