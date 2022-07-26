package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
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

    public void save(ArticleDTO articleDTO, User user) {
        articleRepository.save(articleDTO.toEntity(user));
    }

    public List<ArticleDTO> findAllArticle() {
        return articleRepository.findAll().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    public Long deleteArticle(Long id) {
        articleRepository.deleteById(id);
        return id;
    }
}
