package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    void deleteById(String id);

    Optional<Article> findById(String id);

    List<Article> findAllByAuthor(User author);
}
