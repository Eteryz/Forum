package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Optional<List<Comment>> findCommentsByArticleId(String article_id);
}
