package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.exception.ConfirmationNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;

public interface ConfirmationTokenService {
    ConfirmationToken save(String username) throws UserNotFoundException;

    void activate(String confirmationToken) throws ConfirmationNotFoundException;
}
