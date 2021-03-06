package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;

    @Override
    public CommentDTO save(CommentDTO commentDTO, String username, String articleId) {
        User user = userService.getOneUserByUsername(username);
        Article article = articleService.getOneById(articleId);
        Comment comment = commentDTO.toEntity(user, article);
        return CommentDTO.toModel(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getAllComment() {
        return commentRepository.findAll().stream()
                .map(CommentDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getAllCommentOnArticle(String articleId) throws CommentNotFoundException {
        List<Comment> comments = commentRepository.findCommentsByArticleId(articleId)
                .orElseThrow(() -> new CommentNotFoundException(("There are no comments on the article with Id" + articleId)));
        return comments.stream()
                .map(CommentDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id;
    }
}
