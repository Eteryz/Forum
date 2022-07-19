package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.repository.CommentRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    @Override
    public void save(Comment comment, Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found!"));
        comment.setUser(user);
        comment.setArticle(article);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDTO> findAllComment() {
        return commentRepository.findAll().stream()
                .map(CommentDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Long deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id;
    }
}
