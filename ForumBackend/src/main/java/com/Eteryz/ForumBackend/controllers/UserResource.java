package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return new ResponseEntity<>(UserDTO.toModel(userService.getOneUserById(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getUserInfo/{username}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable String username) {
        return new ResponseEntity<>(UserDTO.toModel(userService.getOneUserByUsername(username)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateByUsername")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO user) {
        try {
            User currentUser = userService.getOneUserById(user.getId());
            currentUser = user.toEntity(currentUser);
            return ResponseEntity.ok(userService.updateUser(currentUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("Произошла ошибка при обновлении информации о пользователе");
        }
    }

   @PreAuthorize("hasRole('USER')")
   @PostMapping("/updateProfileImage")
   public ResponseEntity<?> updateProfileImage(HttpServletRequest request,
                                               @RequestParam("image") MultipartFile file) {
       try {
           String username = jwtUtils.getUserNameFromJwtCookies(request);
           User currentUser = userService.getOneUserByUsername(username);
           currentUser.setAvatar(file.getBytes());
           return ResponseEntity.ok(userService.updateUser(currentUser));
       } catch (Exception e) {
           return ResponseEntity.badRequest()
                   .body("Произошла ошибка при обновлении фотографии пользователя");
       }
   }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка при удалении пользователя");
        }
    }
}
