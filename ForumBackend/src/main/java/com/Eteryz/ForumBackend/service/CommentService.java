package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.dto.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {

    CommentDTO save(CommentDTO commentDTO, String username, String articleId) throws UserNotFoundException, ArticleNotFoundException;

    List<CommentDTO> getAllComment();

    List<CommentDTO> getAllCommentOnArticle(String articleId) throws CommentNotFoundException;

}
