package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.Article;
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


    public static ArticleDTO toModel(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        return articleDTO;
    }

    public Article toEntity() {
        Article article = new Article();
        BeanUtils.copyProperties(this, article);
        return article;
    }
}
