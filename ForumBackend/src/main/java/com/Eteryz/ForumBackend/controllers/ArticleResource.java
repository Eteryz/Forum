package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleResource {

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
    public ResponseEntity<List<ArticleDTO>> getAllArticlesByAuthor(HttpServletRequest request) {
        String username = jwtUtils.getUserNameFromJwtCookies(request);
        User user = userService.getOneUserByUsername(username);
        return new ResponseEntity<>(articleService.findAllArticlesByAuthor(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable String id) {
        return new ResponseEntity<>(ArticleDTO.toModel(articleService.getOneById(id)), HttpStatus.OK);
    }

    @GetMapping("/addToFavorites/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addToFavorites(HttpServletRequest request, @PathVariable String articleId) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            System.out.println(articleId);
            Article article = articleService.getOneById(articleId);
            userService.addToFavorites(username, article);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Произошла ошибка gри добавлении статьи в избранное!");
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addArticle(HttpServletRequest request, @RequestBody ArticleDTO articleDTO) {
        String username = jwtUtils.getUserNameFromJwtCookies(request);
        User user = userService.getOneUserByUsername(username);
        articleService.save(articleDTO, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteArticle(@PathVariable String id) {
        try {
            return ResponseEntity.ok(articleService.deleteArticle(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении статьи");
        }
    }
}
