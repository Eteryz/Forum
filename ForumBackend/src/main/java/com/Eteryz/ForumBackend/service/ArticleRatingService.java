package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ArticleRatingService {

    void likeAndDislikeArticle(String username, String articleId, boolean status) throws ArticleNotFoundException, UserNotFoundException;

    Optional<Boolean> getLikeOrDislikeArticleClickedByUser(String username, String articleId) throws UserNotFoundException, ArticleNotFoundException;
}
