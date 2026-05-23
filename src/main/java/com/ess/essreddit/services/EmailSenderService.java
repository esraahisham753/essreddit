package com.ess.essreddit.services;

import com.ess.essreddit.exceptions.EssRedditException;
import com.ess.essreddit.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final EmailBuilderService emailBuilderService;

    @Async
    void sendEmail(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("no-reply@demomailtrap.co");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(emailBuilderService.build(notificationEmail.getBody()));
        };

        try {
            mailSender.send(mimeMessagePreparator);
            log.info("Email was sent successfully");
        } catch (MailException e) {
            log.error("An error occurred while sending an email", e);
            throw new EssRedditException("Failed to send an email to " + notificationEmail.getRecipient());
        }
    }
}
