package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    void save(Comment comment, Long userId, Long articleId);

    List<CommentDTO> findAllComment();

    Long deleteComment(Long id);
}
