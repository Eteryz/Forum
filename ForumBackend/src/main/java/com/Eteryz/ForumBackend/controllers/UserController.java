package com.Eteryz.ForumBackend.controllers;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.payload.response.MessageResponse;
import com.Eteryz.ForumBackend.security.jwt.JwtUtils;
import com.Eteryz.ForumBackend.service.UserService;
import com.Eteryz.ForumBackend.types.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/allExisting")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllExistingUsers() {
        return new ResponseEntity<>(userService.findAllDeleteOrExistsUsers(false), HttpStatus.OK);
    }

    @GetMapping("/allDeleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllDeletedUsers() {
        return new ResponseEntity<>(userService.findAllDeleteOrExistsUsers(true), HttpStatus.OK);
    }

    @GetMapping("/getUserInfo/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        try {
            return new ResponseEntity<>(userService.getOneUserByUsername(username), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/updateByUsername")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(HttpServletRequest request,@Valid @RequestBody UserDTO user) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return ResponseEntity.ok(userService.updateUser(username, user));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/updateProfileImage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfileImage(HttpServletRequest request,
                                                @RequestParam("image") MultipartFile file) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            return ResponseEntity.ok(userService.updateAvatarUser(username, file));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Ошибка при чтении файла!");
        }
    }

    @DeleteMapping("/delete/myAccount")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteUserByUsername(HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteProfileImage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteProfileImage(HttpServletRequest request) {
        try {
            String username = jwtUtils.getUserNameFromJwtCookies(request);
            userService.deleteProfileImage(username);
            return ResponseEntity.ok(new MessageResponse("ProfileImage deleted successfully"));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/addAdminRole/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addAdminRoleToUser(@PathVariable String username) {
        try {
            return ResponseEntity.ok().body(new MessageResponse("Role " + userService.addRoleToUser(username, ERole.ROLE_ADMIN)));
        } catch (UserRoleNotFoundException | UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
