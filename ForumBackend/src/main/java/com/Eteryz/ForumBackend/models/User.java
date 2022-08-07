package com.Eteryz.ForumBackend.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    @NotBlank
    @Size(max=30)
    private String username;
    @NotBlank
    @Size(max = 120, min = 8)
    private String password;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$")
    private String phone;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] avatar;
    private String city;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Article> articles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments;

    //TODO сделать дату создания аккаунта
    @Column(updatable=false)
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    @ToString.Exclude
    private Set<Article> favorites = new HashSet<>();

    public void addArticleToFavorites(Article article){
        this.favorites.add(article);
        article.getSubscribers().add(this);
    }

    public void removeArticleFromFavorites(Article article){
        this.favorites.remove(article);
        article.getSubscribers().remove(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ArticleRating> articleRatings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

}
