package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/article")
@AllArgsConstructor
public class ArticleResource {

    private ArticleService articleService;

    @GetMapping("/all")
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return new ResponseEntity<>(articleService.findAllArticle(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addArticle(@RequestBody ArticleDTO articleDTO) {
        articleService.save(articleDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(articleService.deleteArticle(id));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении статьи");
        }
    }
}
