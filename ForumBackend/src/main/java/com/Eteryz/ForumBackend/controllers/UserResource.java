package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserResource {

    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            userService.save(user);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при добавлении пользователя");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserDTO> getAllUsers(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.OK);
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
