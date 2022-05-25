package com.Eteryz.ForumBackend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String login;
    private String email;
    private String phone;
    private String avatar;
    private String city;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<ArticleEntity> articles;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<CommentEntity> comments;
}
