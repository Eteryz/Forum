package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.entity.Article;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.PermissionException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.service.ArticleService;
import com.Eteryz.ForumBackend.service.UserService;
import com.Eteryz.ForumBackend.types.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserService userService;

    @Override
    public ArticleDTO save(ArticleDTO articleDTO,String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        return ArticleDTO.fromEntity(articleRepository.save(articleDTO.toEntity(user)));
    }

    @Override
    public List<ArticleDTO> findAllArticle() {
        return articleRepository.findAll().stream()
                .map(ArticleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findAllArticlesByAuthor(String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        return articleRepository.findAllByAuthor(user).stream()
                .map(ArticleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteArticle(String articleId, String username) throws PermissionException, ArticleNotFoundException, UserNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user.getArticles().stream().anyMatch(x -> x.getId().equals(articleId)) ||
                user.getRoles().stream().anyMatch(x -> x.getName().equals(ERole.ROLE_ADMIN))) {
            user = getArticleById(articleId).getAuthor();
            if (articleRepository.existsById(articleId)) {
                user.getArticles().remove(getArticleById(articleId));
                userService.save(user);
                return true;
            } else
                return false;
        }else{
            throw new PermissionException("You do not have permission to delete the article!");
        }
    }

    @Override
    public ArticleDTO getOneById(String id) throws ArticleNotFoundException {
        return ArticleDTO.fromEntity(getArticleById(id));
    }

    @Override
    public Article getArticleById(String id) throws ArticleNotFoundException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(("Article by id " + id + " was not found!")));
    }

    @Override
    public void addToFavorites(String username, String articleId) throws FavoritesException, UserNotFoundException, ArticleNotFoundException {
        User user = userService.getUserByUsername(username);
        Article article = getArticleById(articleId);
        if (!article.getAuthor().equals(user)) {
            user.addArticleToFavorites(article);
            userService.save(user);
        } else {
            throw new FavoritesException("You can't add your article to favorites!");
        }
    }

    @Override
    public void deleteArticleFromFavorites(String username, String articleId) throws UserNotFoundException, ArticleNotFoundException {
        User user = userService.getUserByUsername(username);
        Article article = getArticleById(articleId);
        user.removeArticleFromFavorites(article);
        userService.save(user);
    }

    @Override
    public List<ArticleDTO> getArticleIdFromFavorites(String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        return user.getFavorites().stream()
                .map(ArticleDTO::fromEntity)
                .collect(Collectors.toList());
    }

}

