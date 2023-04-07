package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.entity.Article;
import com.Eteryz.ForumBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {

    void deleteById(String id);

    List<Article> findAllByAuthor(User author);
}
