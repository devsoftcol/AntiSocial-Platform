package com.antisocial.system;

import com.antisocial.model.OnRegistrationCompleteEvent;
import com.antisocial.model.User;
import com.antisocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    /**
     * Triggers upon registration.
     * Sends an email with an activation token to the user's email.
     *
     *  @author Ant Kaynak - Github/Exercon
     * */
  
    @Autowired
    private UserService userService;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);
         
        String recipientAddress = user.getEmail();
        String subject = "AntiSocial Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = "Hi "+user.getName()+" ,\n\nPlease confirm your e-mail with this link. ";
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\n\n" + confirmationUrl + "\n\n\nw/ AntiSocial Team");
        mailSender.send(email);
    }
}