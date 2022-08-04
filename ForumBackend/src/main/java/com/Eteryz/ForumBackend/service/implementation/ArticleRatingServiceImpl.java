package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ArticleRating;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.ArticleRatingRepository;
import com.Eteryz.ForumBackend.service.ArticleRatingService;
import com.Eteryz.ForumBackend.service.ArticleService;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleRatingServiceImpl implements ArticleRatingService {

    private final UserService userService;
    private final ArticleService articleService;
    private final ArticleRatingRepository articleRatingRepository;

    @Override
    public void likeAndDislikeArticle(String username, String articleId, boolean status) {
        User user = userService.getOneUserByUsername(username);
        Article article = articleService.getOneById(articleId);
        Optional<ArticleRating> articleRating =
                articleRatingRepository.findArticleRatingByArticleAndUser(article,user);
        if(articleRating.isPresent()){
            ArticleRating rating = articleRating.get();
            if(rating.isStatus() == status){
                articleRatingRepository.delete(rating);
            }else{
                rating.setStatus(status);
                articleRatingRepository.save(rating);
            }
        }else{
            ArticleRating rating = new ArticleRating();
            rating.setUser(user);
            rating.setArticle(article);
            rating.setStatus(status);
            articleRatingRepository.save(rating);
        }
    }

    @Override
    public Optional<Boolean> getLikeOrDislikeArticleClickedByUser(String username, String articleId) {
        User user = userService.getOneUserByUsername(username);
        Article article = articleService.getOneById(articleId);
        Optional<ArticleRating> articleRating =
                articleRatingRepository.findArticleRatingByArticleAndUser(article,user);
        return articleRating.map(ArticleRating::isStatus);
    }
}
