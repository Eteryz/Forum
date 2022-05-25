package com.Eteryz.ForumBackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10000)
    private String text;
    private Integer likes;
    private  Integer dislikes;

    @ManyToOne
    @JoinColumn(name = "articles_id")
    private ArticleEntity article;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;
}
