package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.Role;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.ConfirmationNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.exception.UserRoleNotFoundException;
import com.Eteryz.ForumBackend.repository.ConfirmationTokenRepository;
import com.Eteryz.ForumBackend.repository.RoleRepository;
import com.Eteryz.ForumBackend.runnable.DeleteConfirmationTokenRunnable;
import com.Eteryz.ForumBackend.service.ConfirmationTokenService;
import com.Eteryz.ForumBackend.service.UserService;
import com.Eteryz.ForumBackend.types.ERole;
import com.Eteryz.ForumBackend.types.EStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken save(String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        user.setStatus(EStatus.CREATED);
        userService.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        new Thread(
                new DeleteConfirmationTokenRunnable(confirmationToken,confirmationTokenRepository)
        ).start();
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void activate(String confirmationToken) throws ConfirmationNotFoundException, UserRoleNotFoundException {
        ConfirmationToken confirm =
                confirmationTokenRepository.
                        findByConfirmationToken(confirmationToken)
                        .orElseThrow(
                                        ConfirmationNotFoundException::new
                        );
        User user = confirm.getUser();
        user.setStatus(EStatus.ACTIVE);
        if(user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new UserRoleNotFoundException("Error: Role" + ERole.ROLE_USER + " is not found."));
            user.getRoles().add(userRole);
        }
        userService.save(user);
        confirmationTokenRepository.delete(confirm);
    }

    @Override
    public boolean exists(User user) {
       return confirmationTokenRepository.existsByUser(user);
    }
}
