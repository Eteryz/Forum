package com.Eteryz.ForumBackend.controller;

import com.Eteryz.ForumBackend.entity.CommentEntity;
import com.Eteryz.ForumBackend.model.Comment;
import com.Eteryz.ForumBackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentResource {

    private final CommentService commentService;

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllArticles(){
        return new ResponseEntity<>(commentService.findAllComment(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addComment(@RequestBody CommentEntity commentEntity,
                                        @RequestParam Long userId,
                                        @RequestParam Long articleId){
        try{
            return ResponseEntity.ok(
                    commentService.addComment(commentEntity, userId, articleId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка при добавлении комментария");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        try{
            return ResponseEntity.ok(
                    commentService.deleteComment(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении комментария");
        }
    }
}
