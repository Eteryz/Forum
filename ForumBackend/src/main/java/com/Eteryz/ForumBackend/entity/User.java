package com.Eteryz.ForumBackend.entity;

import com.Eteryz.ForumBackend.types.EStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
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
@SQLDelete(sql = "UPDATE users SET status = 'DELETED' WHERE id=?")
@FilterDef(name = "deletedUserFilter",
        parameters = @ParamDef(name = "status", type = "string"))
@Filter(name = "deletedUserFilter", condition = " :status=status ")
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
    private String location;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.CREATED;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Article> articles = new LinkedList<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> comments = new LinkedList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    @ToString.Exclude
    private List<Article> favorites = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ArticleRating> articleRatings = new LinkedList<>();

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false))
    @ToString.Exclude
    private Set<Role> roles;

    public void addArticleToFavorites(Article article){
        this.favorites.add(article);
        article.getSubscribers().add(this);
    }

    public void removeArticleFromFavorites(Article article){
        this.favorites.remove(article);
        article.getSubscribers().remove(this);
    }
}
