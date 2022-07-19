package com.Eteryz.ForumBackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10000)
    private String text;
    private Integer likes;
    private Integer dislikes;

    @ManyToOne
    @JoinColumn(name = "articles_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;
}
