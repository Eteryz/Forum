package com.Eteryz.ForumBackend.runnable;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class DeleteConfirmationTokenRunnable implements Runnable{

    private final ConfirmationToken confirmationToken;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void run() {
            try {
                //Delete mail confirmation token 5 minutes after sending
                Thread.sleep(1000*60*5);
                log.info("Task DeleteConfirmationTokenRunnable run!");
                Optional<ConfirmationToken> confirm = confirmationTokenRepository.
                                findByConfirmationToken(confirmationToken.getConfirmationToken());
                confirm.ifPresent(confirmationTokenRepository::delete);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
