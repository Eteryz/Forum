package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO save(CommentDTO commentDTO, String username, String articleId);

    List<CommentDTO> getAllComment();

    List<CommentDTO> getAllCommentOnArticle(String articleId) throws CommentNotFoundException;

    Long deleteComment(Long id);
}
