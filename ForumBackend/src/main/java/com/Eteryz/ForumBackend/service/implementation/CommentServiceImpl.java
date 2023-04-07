package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.CommentDTO;
import com.Eteryz.ForumBackend.exception.ArticleNotFoundException;
import com.Eteryz.ForumBackend.exception.CommentNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.Comment;
import com.Eteryz.ForumBackend.models.types.ERole;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.ArticleRepository;
import com.Eteryz.ForumBackend.repository.CommentRepository;
import com.Eteryz.ForumBackend.service.CommentService;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;
    private final ArticleRepository articleRepository;

    @Override
    public CommentDTO save(CommentDTO commentDTO, String username, String articleId) throws UserNotFoundException, ArticleNotFoundException {
        User user = userService.getUserByUsername(username);
        Article article = getArticleById(articleId);
        Comment comment = commentDTO.toEntity(user, article);
        return CommentDTO.fromEntity(commentRepository.save(comment));
    }

    @Override
    public List<CommentDTO> getAllCommentOnArticle(String articleId) throws CommentNotFoundException {
        List<Comment> comments = commentRepository.findCommentsByArticleId(articleId)
                .orElseThrow(() -> new CommentNotFoundException(("There are no comments on the article with Id" + articleId)));
        return comments.stream()
                .map(CommentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(String username, String commentId) throws UserNotFoundException, CommentNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user.getComments()
                .stream()
                .anyMatch(comment -> comment.getId().equals(commentId)) ||
                user.getRoles()
                        .stream()
                        .anyMatch(x -> x.getName().equals(ERole.ROLE_ADMIN))
        ) {
            Comment comment = getCommentById(commentId);
            commentRepository.deleteById(commentId);
            user = comment.getUser();
            user.getComments().remove(comment);
            userService.save(user);
            return true;
        }else{
            return false;
        }
    }

    private Comment getCommentById(String id) throws CommentNotFoundException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(("Comment by id= " + id + " was not found!")));
    }
    private Article getArticleById(String id) throws ArticleNotFoundException {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(("Article by id " + id + " was not found!")));
    }

}
