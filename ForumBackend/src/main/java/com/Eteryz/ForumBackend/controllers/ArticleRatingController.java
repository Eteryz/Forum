package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.ArticleRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
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
                                                @RequestBody boolean status)
    {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            articleRatingService.likeAndDislikeArticle(username,articleId, status);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/likeOrDislikeArticle/{articleId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getLikeOrDislikeClickedByUser (HttpServletRequest request,
                                                            @PathVariable String articleId)
    {
        String username = jwtUtils.getUserNameFromJwtCookies(request);
        return ResponseEntity.ok(articleRatingService.getLikeOrDislikeArticleClickedByUser(username,articleId));
    }
}
