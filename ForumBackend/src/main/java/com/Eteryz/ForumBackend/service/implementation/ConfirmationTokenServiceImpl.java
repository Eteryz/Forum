package com.Eteryz.ForumBackend.service.implementation;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.entity.User;
import com.Eteryz.ForumBackend.exception.ConfirmationNotFoundException;
import com.Eteryz.ForumBackend.exception.UserNotFoundException;
import com.Eteryz.ForumBackend.repository.ConfirmationTokenRepository;
import com.Eteryz.ForumBackend.service.ConfirmationTokenService;
import com.Eteryz.ForumBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final UserService userService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken save(String username) throws UserNotFoundException {
        User user = userService.getUserByUsername(username);
        user.setDeleted(true);
        userService.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        return  confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void activate(String confirmationToken) throws ConfirmationNotFoundException {
        ConfirmationToken confirm =
                confirmationTokenRepository.
                        findByConfirmationToken(confirmationToken)
                        .orElseThrow(
                                        ConfirmationNotFoundException::new
                        );
        User user = confirm.getUser();
        user.setDeleted(false);
        userService.save(user);
        confirmationTokenRepository.delete(confirm);
    }
}
