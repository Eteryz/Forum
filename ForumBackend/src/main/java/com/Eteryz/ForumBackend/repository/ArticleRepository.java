package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity,Long> {
}
