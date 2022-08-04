package com.Eteryz.ForumBackend.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "article_rating")
public class ArticleRating {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "articles_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    private boolean status;
}
