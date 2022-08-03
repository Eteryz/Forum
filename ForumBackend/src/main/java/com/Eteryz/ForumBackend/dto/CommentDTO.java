package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String text;
    private LocalDateTime date_creation;
    private String author;

    public static CommentDTO toModel(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        commentDTO.setAuthor(comment.getUser().getUsername());
        return commentDTO;
    }

    public Comment toEntity(User user, Article article) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        comment.setUser(user);
        comment.setArticle(article);
        return comment;
    }
}
