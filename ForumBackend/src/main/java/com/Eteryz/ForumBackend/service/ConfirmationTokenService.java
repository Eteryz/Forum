package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.ConfirmationNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;

public interface ConfirmationTokenService {
    ConfirmationToken save(String username) throws UserNotFoundException, ConfirmationNotFoundException;

    void activate(String confirmationToken) throws ConfirmationNotFoundException, UserRoleNotFoundException;

    boolean exists(User user);
}
