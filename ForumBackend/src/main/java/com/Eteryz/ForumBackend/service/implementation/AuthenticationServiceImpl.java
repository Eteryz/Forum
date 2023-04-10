package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.Role;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.*;
import com.Eteryz.ForumBackend.payload.request.LoginRequest;
import com.Eteryz.ForumBackend.payload.request.SignupRequest;
import com.Eteryz.ForumBackend.repository.RoleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.security.service.UserDetailsImpl;
import com.Eteryz.ForumBackend.service.AuthenticationService;
import com.Eteryz.ForumBackend.service.ConfirmationTokenService;
import com.Eteryz.ForumBackend.service.EmailService;
import com.Eteryz.ForumBackend.service.UserService;
import com.Eteryz.ForumBackend.types.ERole;
import com.Eteryz.ForumBackend.types.EStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserService userService;

    private final PasswordEncoder encoder;

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;

    @Value("${spring.server.url}")
    private String baseURL;


    @Override
    public void register(SignupRequest signUpRequest) throws UserAlreadyExistException, UserRoleNotFoundException, MessagingException, UnsupportedEncodingException, UserNotFoundException, ConfirmationNotFoundException {
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

        ConfirmationToken confirmationToken = confirmationTokenService.save(signUpRequest.getUsername());

        emailService.sendMessageWithAttachment(
                signUpRequest.getEmail(),
                "Email Verification",
                "<h1>" +
                        "To confirm your account, please click here: \n" +
                        baseURL + "api/auth/confirm-account?token=" +
                        confirmationToken.getConfirmationToken() +
                        "</h1>",
                null
        );
    }

    @Override
    public UserDetailsImpl authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @Override
    public boolean restore(LoginRequest loginRequest) throws UserNotFoundException, MessagingException, UnsupportedEncodingException, ConfirmationNotFoundException, ConfirmationExistsException {
        User user = userService.getUserByUsername(loginRequest.getUsername());
        if(user.getStatus().equals(EStatus.ACTIVE))
            return false;
        if(!encoder.matches(loginRequest.getPassword(), user.getPassword()))
            return false;

        if(confirmationTokenService.exists(user))
            throw new ConfirmationExistsException("The message has already been sent, you can repeat it in 5 minutes");
        ConfirmationToken confirmationToken = confirmationTokenService.save(loginRequest.getUsername());
        emailService.sendMessageWithAttachment(
                user.getEmail(),
                "Access recovery",
                "<h1>" +
                        "To confirm your account, please click here: \n" +
                        baseURL + "api/auth/confirm-account?token=" +
                        confirmationToken.getConfirmationToken() +
                        "</h1>",
                null
        );
        return true;
    }

    @Override
    public void mailActivate(String confirmationToken) throws ConfirmationNotFoundException, UserRoleNotFoundException {
        confirmationTokenService.activate(confirmationToken);
    }
}
