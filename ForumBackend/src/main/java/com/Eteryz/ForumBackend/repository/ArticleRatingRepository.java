package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ArticleRating;
import com.Eteryz.ForumBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRatingRepository extends JpaRepository<ArticleRating, String> {

    Optional<ArticleRating> findArticleRatingByArticleAndUser(Article article, User user);

}
