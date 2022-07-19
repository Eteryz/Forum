package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentResource {

    private CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<CommentDTO>> getAllArticles() {
        return new ResponseEntity<>(commentService.findAllComment(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody Comment comment,
                                        @RequestParam Long userId,
                                        @RequestParam Long articleId) {
        try {
            commentService.save(comment, userId, articleId);
            return ResponseEntity.ok().build();
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
