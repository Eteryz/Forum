package com.Eteryz.ForumBackend.dto;

import com.Eteryz.ForumBackend.models.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private String id;
    private String text;
    private Integer likes;
    private Integer dislikes;

    public static CommentDTO toModel(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        return commentDTO;
    }

    public Comment toEntity() {
        Comment comment = new Comment();
        BeanUtils.copyProperties(this, comment);
        return comment;
    }
}
