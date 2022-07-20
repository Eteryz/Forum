package com.Eteryz.ForumBackend.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @NonNull
    @NotBlank
    @Size(max=30)
    private String username;
    @NonNull
    @NotBlank
    @Size(max = 120, min = 8)
    private String password;
    @NonNull
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String phone;
    private String avatar;
    private String city;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> comments;
}
