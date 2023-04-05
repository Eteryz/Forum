package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.payload.request.LoginRequest;
import com.Eteryz.ForumBackend.payload.request.SignupRequest;
import com.Eteryz.ForumBackend.security.service.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    void register(SignupRequest signUpRequest) throws UserAlreadyExistException, UserRoleNotFoundException;
    UserDetailsImpl authenticate(LoginRequest loginRequest);
}
