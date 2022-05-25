package com.Eteryz.ForumBackend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "articles")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    private Integer likes;
    private Integer dislikes;
    @Column(length = 5000)
    private String tag;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CommentEntity> comments;
}
