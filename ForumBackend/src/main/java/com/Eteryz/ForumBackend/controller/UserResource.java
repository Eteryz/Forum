package com.Eteryz.ForumBackend.controller;

import com.Eteryz.ForumBackend.entity.UserEntity;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.model.User;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserEntity userEntity){
        try {
            return ResponseEntity.ok(userService.addUser(userEntity));
        }catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getAllUsers(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.getOneUser(id), HttpStatus.OK);
    }
}
