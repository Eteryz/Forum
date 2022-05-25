package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.entity.ArticleEntity;
import com.Eteryz.ForumBackend.entity.CommentEntity;
import com.Eteryz.ForumBackend.entity.UserEntity;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.model.Comment;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.repository.CommentRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    public Comment addComment(CommentEntity commentEntity, Long userId, Long articleId){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found!"));
        ArticleEntity article = articleRepository.findById(articleId)
                .orElseThrow(()-> new ArticleNotFoundException("Article not found!"));
        commentEntity.setUser(user);
        commentEntity.setArticle(article);
        return Comment.toModel(commentRepository.save(commentEntity));
    }

    public List<Comment> findAllComment(){
        return commentRepository.findAll().stream()
                .map(Comment::toModel)
                .collect(Collectors.toList());
    }

    public Long deleteComment(Long id) {
        commentRepository.deleteById(id);
        return id;
    }
}
