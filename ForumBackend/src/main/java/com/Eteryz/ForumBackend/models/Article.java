package com.Eteryz.ForumBackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "articles_id")
    private String id;
    private String title;
    @Type(type = "org.hibernate.type.TextType")
    private String text;
    @Formula(value = "(select count(a.users_id) from article_rating a where a.status = true and a.articles_id = articles_id)")
    private int likes;
    @Formula(value = "(select count(a.users_id) from article_rating a where a.status = false and a.articles_id = articles_id)")
    private int dislikes;
    @Column(length = 5000)
    private String tag;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @ToString.Exclude
    private User author;

    @ManyToMany(mappedBy = "favorites")
    private Set<User> subscribers = new HashSet<>();

    @PreRemove
    public void preRemove(){
        subscribers.forEach(user -> user.removeArticleFromFavorites(this));
    }

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ArticleRating> articleRatings;
}
