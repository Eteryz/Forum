package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.*;
import com.Eteryz.ForumBackend.payload.request.LoginRequest;
import com.Eteryz.ForumBackend.payload.request.SignupRequest;
import com.Eteryz.ForumBackend.security.service.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface AuthenticationService {
    void register(SignupRequest signUpRequest) throws UserAlreadyExistException, UserRoleNotFoundException, MessagingException, UnsupportedEncodingException, UserNotFoundException, ConfirmationNotFoundException;
    UserDetailsImpl authenticate(LoginRequest loginRequest);

    boolean restore(LoginRequest loginRequest) throws UserNotFoundException, MessagingException, UnsupportedEncodingException, ConfirmationNotFoundException, ConfirmationExistsException;

    void mailActivate(String confirmationToken) throws ConfirmationNotFoundException, UserRoleNotFoundException;
}
