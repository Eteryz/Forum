package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.ArticleService;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAllArticle(), HttpStatus.OK);
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllArticlesByAuthor(HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            User user = userService.getOneUserByUsername(username);
            return new ResponseEntity<>(articleService.findAllArticlesByAuthor(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getArticleById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(ArticleDTO.toModel(articleService.getOneById(id)), HttpStatus.OK);
        } catch (ArticleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/allArticlesFromFavorites")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllArticleIdFromFavorites(HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return new ResponseEntity<>(userService.getArticleIdFromFavorites(username), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/addToFavorites/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToFavorites(HttpServletRequest request, @PathVariable String articleId) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            Article article = articleService.getOneById(articleId);
            userService.addToFavorites(username, article);
            return ResponseEntity.ok().build();
        } catch (ArticleNotFoundException | FavoritesException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteArticleFromFavorites/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteArticleFromFavorites(HttpServletRequest request, @PathVariable String articleId) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            Article article = articleService.getOneById(articleId);
            userService.deleteArticleFromFavorites(username, article);
            return ResponseEntity.ok().build();
        }  catch (ArticleNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addArticle(HttpServletRequest request, @RequestBody ArticleDTO articleDTO) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            User user  = userService.getOneUserByUsername(username);
            articleService.save(articleDTO, user);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteArticle(@PathVariable String id) {
            return ResponseEntity.ok(articleService.deleteArticle(id));
    }
}
