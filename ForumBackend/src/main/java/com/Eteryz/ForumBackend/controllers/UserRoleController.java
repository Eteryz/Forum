package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/userRole")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserService userService;

    @GetMapping("/admin/add/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAdminRoleToUser(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.addRoleToUser(username, ERole.ROLE_ADMIN));
        } catch (UserRoleNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
