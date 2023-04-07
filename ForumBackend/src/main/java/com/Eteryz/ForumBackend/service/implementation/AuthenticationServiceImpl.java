package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.entity.Role;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.payload.request.LoginRequest;
import com.Eteryz.ForumBackend.payload.request.SignupRequest;
import com.Eteryz.ForumBackend.repository.RoleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.security.service.UserDetailsImpl;
import com.Eteryz.ForumBackend.service.AuthenticationService;
import com.Eteryz.ForumBackend.types.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;


    @Override
    public void register(SignupRequest signUpRequest) throws UserAlreadyExistException, UserRoleNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistException("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistException("Error: Email is already in use!");
        }
        // Create new user's account
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new UserRoleNotFoundException("Error: Role" + ERole.ROLE_USER + " is not found."));
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserDetailsImpl authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserDetailsImpl) authentication.getPrincipal();
    }
}
