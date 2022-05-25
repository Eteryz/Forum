package com.Eteryz.ForumBackend.controller;

import com.Eteryz.ForumBackend.model.Article;
import com.Eteryz.ForumBackend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleResource {

    private final ArticleService articleService;

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAllArticles(){
        return new ResponseEntity<>(articleService.findAllArticle(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addArticle(@RequestBody Article article){
        articleService.addArticle(article);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable Long id){
        try{
            return ResponseEntity.ok(articleService.deleteArticle(id));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении статьи");
        }
    }
}
