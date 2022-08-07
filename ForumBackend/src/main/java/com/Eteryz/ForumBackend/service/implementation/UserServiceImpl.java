package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.Role;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.RoleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public void addToFavorites(String username, Article article) throws FavoritesException, UserNotFoundException {
        User user = getOneUserByUsername(username);
        if (!article.getAuthor().equals(user)) {
            user.addArticleToFavorites(article);
            userRepository.save(user);
        } else {
            throw new FavoritesException("You can't add your article to favorites!");
        }
    }

    @Override
    public void deleteArticleFromFavorites(String username, Article article) throws UserNotFoundException {
        User user = getOneUserByUsername(username);
        user.removeArticleFromFavorites(article);
        userRepository.save(user);
    }

    @Override
    public List<ArticleDTO> getArticleIdFromFavorites(String username) throws UserNotFoundException {
        User user = getOneUserByUsername(username);
        return user.getFavorites().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(User user) {
        return UserDTO.toModel(userRepository.save(user));
    }

    @Override
    public User getOneUserById(String id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found!"));
    }

    @Override
    public User getOneUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User by username " + username + " was not found!"));
    }

    @Override
    public void deleteUserByUsername(String username) throws UserNotFoundException {
        User user = getOneUserByUsername(username);
        userRepository.deleteById(user.getId());
    }


    @Override
    public void addRoleToUser(String username, ERole role) throws UserRoleNotFoundException, UserNotFoundException {
        User user = getOneUserByUsername(username);
        Role role1 = roleRepository.findByName(role)
                .orElseThrow(() -> new UserRoleNotFoundException("User role " + role + " was not found!"));
        user.getRoles().add(role1);
        userRepository.save(user);
    }

    @Override
    public void deleteProfileImage(String username) throws UserNotFoundException {
        User user = getOneUserByUsername(username);
        user.setAvatar(null);
        userRepository.save(user);
    }
}
