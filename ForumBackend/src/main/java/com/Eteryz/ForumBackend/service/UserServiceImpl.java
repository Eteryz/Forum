package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.exception.UserAlreadyExistException;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void save(User user) throws UserAlreadyExistException {
        if (userRepository.findByUsername(user.getName()).isPresent())
            throw new UserAlreadyExistException("A user with the same name already exists");
        else
            userRepository.save(user);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        return UserDTO.toModel(userRepository.save(userDTO.toEntity()));
    }

    @Override
    public UserDTO getOneUser(Long id) {
        return UserDTO.toModel(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found!")));
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
