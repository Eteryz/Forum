package com.Eteryz.ForumBackend.runnable;

import com.Eteryz.ForumBackend.entity.ConfirmationToken;
import com.Eteryz.ForumBackend.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;


@RequiredArgsConstructor
@Log4j2
public class DeleteConfirmationTokenRunnable implements Runnable{

    @Value("${spring.time.delete-mail-token}")
    private String SlEEP_TIME;

    private final ConfirmationToken confirmationToken;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void run() {
            try {
                Thread.sleep(Integer.getInteger(SlEEP_TIME));
                log.info("Task DeleteConfirmationTokenRunnable run!");
                Optional<ConfirmationToken> confirm = confirmationTokenRepository.
                                findByConfirmationToken(confirmationToken.getConfirmationToken());
                confirm.ifPresent(confirmationTokenRepository::delete);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
