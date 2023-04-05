package com.Eteryz.ForumBackend.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Column(length = 10000)
    private String text;
    private LocalDateTime date_creation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articles_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

}
