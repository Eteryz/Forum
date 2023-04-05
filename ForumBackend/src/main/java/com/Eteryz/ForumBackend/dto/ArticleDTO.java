package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private String id;
    private String title;
    private String text;
    private Integer likes;
    private Integer dislikes;
    private String tag;
    private String author;
    private Integer numberComments;


    public static ArticleDTO fromEntity(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        articleDTO.author = article.getAuthor().getUsername();
        articleDTO.numberComments = article.getComments().size();
        return articleDTO;
    }

    public Article toEntity(User user) {
        Article article = new Article();
        BeanUtils.copyProperties(this, article);
        article.setAuthor(user);
        return article;
    }
}
