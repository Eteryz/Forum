package com.Eteryz.ForumBackend.service;

import com.Eteryz.ForumBackend.dto.ArticleDTO;
import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.FavoritesException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.models.Article;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public void addToFavorites(String username, Article article) throws FavoritesException {
        User user = getOneUserByUsername(username);
        if (!article.getAuthor().equals(user)) {
            user.getFavorites().add(article);
            userRepository.save(user);
        } else {
            throw new FavoritesException("You can't add your article to favorites!");
        }
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
    public User getOneUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + " was not found!"));
    }

    @Override
    public User getOneUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User by username " + username + " was not found!"));
    }

    @Override
    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public void deleteArticleFromFavorites(String username, Article article) {
        User user = getOneUserByUsername(username);
        user.getFavorites().remove(article);
        userRepository.save(user);
    }

    @Override
    public List<ArticleDTO> getArticleIdFromFavorites(String username) {
        User user = getOneUserByUsername(username);
        return user.getFavorites().stream()
                .map(ArticleDTO::toModel)
                .collect(Collectors.toList());
    }
}
