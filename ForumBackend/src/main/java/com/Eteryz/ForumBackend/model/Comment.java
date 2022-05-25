package com.Eteryz.ForumBackend.model;

import com.Eteryz.ForumBackend.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment{

    private Long id;
    private String text;
    private Integer likes;
    private Integer dislikes;

    public static Comment toModel(CommentEntity commentEntity) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentEntity, comment);
        return comment;
    }

    public CommentEntity toEntity() {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(this, commentEntity);
        return commentEntity;
    }
}
