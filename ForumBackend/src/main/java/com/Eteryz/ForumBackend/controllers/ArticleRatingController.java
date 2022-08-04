package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.ArticleRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/articleRating")
@RequiredArgsConstructor
public class ArticleRatingController {

    private final ArticleRatingService articleRatingService;

    private final JwtUtils jwtUtils;

    @PostMapping("/likeAndDislikeArticle/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> likeAndUnlikePost(HttpServletRequest request,
                                               @PathVariable String articleId,
                                               @RequestBody boolean status) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            articleRatingService.likeAndDislikeArticle(username, articleId, status);
            return ResponseEntity.ok().build();
        } catch (ArticleNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/likeOrDislikeArticle/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getLikeOrDislikeClickedByUser(HttpServletRequest request,
                                                           @PathVariable String articleId) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return ResponseEntity.ok(articleRatingService.getLikeOrDislikeArticleClickedByUser(username, articleId));
        } catch (ArticleNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
