package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.dto.UserDTO;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.models.types.ERole;
import com.Eteryz.ForumBackend.models.Role;
import com.Eteryz.ForumBackend.models.User;
import com.Eteryz.ForumBackend.repository.RoleRepository;
import com.Eteryz.ForumBackend.repository.UserRepository;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final EntityManager entityManager;

    @Override
    public List<UserDTO> findAllDeleteOrExistsUsers(boolean isDeleted) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedUserFilter");
        filter.setParameter("isDeleted", isDeleted);
        List<User> users =  userRepository.findAll();
        session.disableFilter("deletedUserFilter");
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public  UserDTO save(User user){
        return UserDTO.fromEntity(userRepository.save(user));
    }

    @Override
    public List<UserDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String username, UserDTO user) throws UserNotFoundException {
        return UserDTO.fromEntity(
                userRepository.save(
                        user.toEntity(
                                getUserByUsername(username)
                        )
                )
        );
    }

    @Override
    public UserDTO updateAvatarUser(String username, MultipartFile file) throws UserNotFoundException, IOException {
        User user = getUserByUsername(username);
        user.setAvatar(file.getBytes());
        return UserDTO.fromEntity(userRepository.save(user));
    }

    @Override
    public UserDTO getOneUserByUsername(String username) throws UserNotFoundException {
        return UserDTO.fromEntity(getUserByUsername(username));
    }

    @Override
    public void deleteUserByUsername(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        userRepository.deleteById(user.getId());
    }


    @Override
    public boolean addRoleToUser(String username, ERole role) throws UserRoleNotFoundException, UserNotFoundException {
        User user = getUserByUsername(username);
        if(user.isDeleted())
            return false;
        Role role1 = roleRepository.findByName(role)
                .orElseThrow(() -> new UserRoleNotFoundException("User role " + role + " was not found!"));
        user.getRoles().add(role1);
        userRepository.save(user);
        return true;
    }

    @Override
    public void deleteProfileImage(String username) throws UserNotFoundException {
        User user = getUserByUsername(username);
        user.setAvatar(null);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User by username " + username + " was not found!"));
    }
}
