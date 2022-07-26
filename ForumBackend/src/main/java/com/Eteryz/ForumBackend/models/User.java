package com.Eteryz.ForumBackend.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> comments;
}
