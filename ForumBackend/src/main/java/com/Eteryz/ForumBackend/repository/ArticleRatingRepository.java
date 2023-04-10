package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.entity.Article;
import com.Eteryz.ForumBackend.entity.ArticleRating;
import com.Eteryz.ForumBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRatingRepository extends JpaRepository<ArticleRating, String> {

    Optional<ArticleRating> findArticleRatingByArticleAndUser(Article article, User user);

}
