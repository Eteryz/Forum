package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.ConfirmationNotFoundException;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.payload.request.LoginRequest;
import com.Eteryz.ForumBackend.payload.request.SignupRequest;
import com.Eteryz.ForumBackend.payload.response.MessageResponse;
import com.Eteryz.ForumBackend.payload.response.UserInfoResponse;
import com.Eteryz.ForumBackend.repository.ConfirmationTokenRepository;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.security.service.UserDetailsImpl;
import com.Eteryz.ForumBackend.service.AuthenticationService;
import com.Eteryz.ForumBackend.service.ConfirmationTokenService;
import com.Eteryz.ForumBackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    private final AuthenticationService authenticationService;

    private final JwtUtils jwtUtils;

    private final EmailService emailService;

    private final ConfirmationTokenService confirmationTokenService;

    @Value("${spring.server.url}")
    private String baseURL;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            UserDetailsImpl userDetails = authenticationService.authenticate(loginRequest);
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(
                            new UserInfoResponse(
                                    userDetails.getId(),
                                    userDetails.getUsername(),
                                    userDetails.getEmail(),
                                    roles
                            )
                    );
        }catch (BadCredentialsException e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            authenticationService.register(signUpRequest);

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
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (UserRoleNotFoundException | UserNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UserAlreadyExistException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(e.getMessage());
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @GetMapping("/confirm-account")
    public ResponseEntity<?> confirmAccount(@RequestParam("token") String confirmationToken) {
        try {
            confirmationTokenService.activate(confirmationToken);
            return ResponseEntity.ok()
                    .body("Mail has been successfully confirmed.");
        } catch (ConfirmationNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ConfirmationToken");
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

}
