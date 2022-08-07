package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.service.ArticleService;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

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
    public void deleteArticle(String articleId, User user) throws ArticleNotFoundException {
        if(articleRepository.existsById(articleId)) {
            user.getArticles().remove(articleRepository.getById(articleId));
            userRepository.save(user);
        }else
            throw new ArticleNotFoundException("Article by id " + articleId + " was not found!");
    }

    @Override
    public void deleteArticle(String articleId) throws ArticleNotFoundException {
        if(articleRepository.existsById(articleId)) {

        }else
            throw new ArticleNotFoundException("Article by id " + articleId + " was not found!");
    }

    @Override
    public Article getOneById(String id) throws ArticleNotFoundException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(("Article by id " + id + " was not found!")));
    }
}
