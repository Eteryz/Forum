package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(UserDTO.toModel(userService.getOneUser(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return new ResponseEntity<>(UserDTO.toModel(userService.getOneUser(username)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateByUsername/{username}")
    public ResponseEntity<?> updateUser(@PathVariable String username,@Valid @RequestBody UserDTO user) {
        try {
            User currentUser = userService.getOneUser(username);
            currentUser = user.toEntity(currentUser);
            return ResponseEntity.ok(userService.updateUser(currentUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Произошла ошибка при обновлении информации о пользователе");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении пользователя");
        }
    }
}
