package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public void save(ArticleDTO articleDTO,User user) {
        articleRepository.save(articleDTO.toEntity(user));
    }

    @Override
    public List<ArticleDTO> findAllArticle() {
        return articleRepository.findAll().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findAllArticlesByAuthor(User user) {
        return articleRepository.findAllByAuthor(user).stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteArticle(String id) {
        articleRepository.deleteById(id);
        return id;
    }

    @Override
    public Article getOneById(String id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(("Article by id " + id + " was not found!")));
    }
}
