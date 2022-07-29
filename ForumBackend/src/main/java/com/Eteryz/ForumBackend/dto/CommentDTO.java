package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.Comment;
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
    private int likes;
    private int dislikes;
    private LocalDateTime date_creation;
    private String author;

    public static CommentDTO toModel(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        commentDTO.setAuthor(comment.getUser().getUsername());
        return commentDTO;
    }

    public Comment toEntity() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }
}
