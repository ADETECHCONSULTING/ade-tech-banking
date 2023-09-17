package fr.adamatraore.banking.adetechbanking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import fr.adamatraore.banking.adetechbanking.dto.EmailDto;
import fr.adamatraore.banking.adetechbanking.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendEmailAlert(EmailDto emailDto) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(emailDto.getTo());
            msg.setFrom(sender);
            msg.setSubject(emailDto.getSubject());
            msg.setText(emailDto.getBody());
            javaMailSender.send(msg);
            System.out.println("Email sent successfully");
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
    
}
