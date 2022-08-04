package com.Eteryz.ForumBackend.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ArticleRatingService {

    void likeAndDislikeArticle(String username, String articleId, boolean status);

    Optional<Boolean> getLikeOrDislikeArticleClickedByUser(String username, String articleId);
}
