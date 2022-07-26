package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
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

    private final JwtUtils jwtUtils;

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAllArticle(), HttpStatus.OK);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> addArticle(HttpServletRequest request, @RequestBody ArticleDTO articleDTO) {
        String username = jwtUtils.getUserNameFromJwtCookies(request);
        User currentUser = userService.getOneUserByUsername(username);
        articleService.save(articleDTO, currentUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articleService.deleteArticle(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении статьи");
        }
    }
}
