package com.Eteryz.ForumBackend.model;

import com.Eteryz.ForumBackend.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private String id;
    private String title;
    private String text;
    private Integer likes;
    private Integer dislikes;
    private String tag;


    public static Article toModel(ArticleEntity articleEntity) {
        Article article = new Article();
        BeanUtils.copyProperties(articleEntity, article);
        return article;
    }

    public ArticleEntity toEntity() {
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(this, articleEntity);
        return articleEntity;
    }
}
