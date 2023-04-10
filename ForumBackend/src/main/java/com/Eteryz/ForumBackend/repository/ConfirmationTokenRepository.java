package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("confirmationTokenRepository")
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByConfirmationToken(String confirmationToken);

    Optional<ConfirmationToken> findByUser(User user);

    boolean existsByUser(User user);
}