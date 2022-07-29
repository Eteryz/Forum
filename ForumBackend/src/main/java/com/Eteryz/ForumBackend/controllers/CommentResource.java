package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    private final JwtUtils jwtUtils;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{articleId}/all")
    public ResponseEntity<?> getAllCommentsOnArticle(@PathVariable String articleId) {
        try {
            return new ResponseEntity<>(commentService.getAllCommentOnArticle(articleId), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{articleId}/add")
    public ResponseEntity<?> addComment(HttpServletRequest request,
                                        @PathVariable String articleId,
                                        @RequestBody CommentDTO commentDTO) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return ResponseEntity.ok(commentService.save(commentDTO, username, articleId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при добавлении комментария");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    commentService.deleteComment(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении комментария");
        }
    }
}
