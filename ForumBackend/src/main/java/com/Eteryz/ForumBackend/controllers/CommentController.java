package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final JwtUtils jwtUtils;

    @GetMapping("/{articleId}/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllCommentsOnArticle(@PathVariable String articleId) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentOnArticle(articleId), HttpStatus.OK);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/{articleId}/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addComment(HttpServletRequest request,
                                        @PathVariable String articleId,
                                        @RequestBody CommentDTO commentDTO) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return ResponseEntity.ok(commentService.save(commentDTO, username, articleId));
        } catch (UserNotFoundException | ArticleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(
                commentService.deleteComment(id));

    }
}
