package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.PermissionException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.payload.response.MessageResponse;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    @Override
    public ArticleDTO save(ArticleDTO articleDTO,String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        return ArticleDTO.toModel(articleRepository.save(articleDTO.toEntity(user)));
    }

    @Override
    public List<ArticleDTO> findAllArticle() {
        return articleRepository.findAll().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDTO> findAllArticlesByAuthor(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        return articleRepository.findAllByAuthor(user).stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteArticle(String articleId, String username) throws PermissionException, ArticleNotFoundException, UserNotFoundException {
        User user = getUserByUsername(username);
        if (user.getArticles().stream().anyMatch(x -> x.getId().equals(articleId)) ||
                user.getRoles().stream().anyMatch(x -> x.getName().equals(ERole.ROLE_ADMIN))) {
            user = getArticleById(articleId).getAuthor();
            if (articleRepository.existsById(articleId)) {
                user.getArticles().remove(getArticleById(articleId));
                userRepository.save(user);
                return true;
            } else
                return false;
        }else{
            throw new PermissionException("You do not have permission to delete the article!");
        }
    }

    @Override
    public ArticleDTO getOneById(String id) throws ArticleNotFoundException {
        return ArticleDTO.toModel(getArticleById(id));
    }

    private Article getArticleById(String id) throws ArticleNotFoundException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(("Article by id " + id + " was not found!")));
    }

    @Override
    public void addToFavorites(String username, String articleId) throws FavoritesException, UserNotFoundException, ArticleNotFoundException {
        User user = getUserByUsername(username);
        Article article = getArticleById(articleId);
        if (!article.getAuthor().equals(user)) {
            user.addArticleToFavorites(article);
            userRepository.save(user);
        } else {
            throw new FavoritesException("You can't add your article to favorites!");
        }
    }

    @Override
    public void deleteArticleFromFavorites(String username, String articleId) throws UserNotFoundException, ArticleNotFoundException {
        User user = getUserByUsername(username);
        Article article = getArticleById(articleId);
        user.removeArticleFromFavorites(article);
        userRepository.save(user);
    }

    @Override
    public List<ArticleDTO> getArticleIdFromFavorites(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        return user.getFavorites().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    private User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User by username " + username + " was not found!"));
    }

}

