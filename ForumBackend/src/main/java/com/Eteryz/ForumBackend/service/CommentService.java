package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.exception.PermissionException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.dto.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO save(CommentDTO commentDTO, String username, String articleId) throws UserNotFoundException, ArticleNotFoundException;

    List<CommentDTO> getAllCommentOnArticle(String articleId) throws CommentNotFoundException;

    boolean delete(String username, String commentId) throws UserNotFoundException;
}
