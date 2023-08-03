package com.diptopaul.blog.services.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.services.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {
	private JavaMailSender mailSender;
	
//	@Value("${spring.mail.username}")
//	private String fromMail;
	
	@Override
	public void sendPasswordResetEmail(UserDto userDto, PasswordResetTokenDto resetTokenDto, String contextPath, Locale locale) {
		 try {
			 //System.out.println(mailSender.get);
			mailSender.send(constructResetTokenEmail(contextPath, locale, resetTokenDto, userDto));
		} catch (MailException e) {
			// Handle any exceptions related to sending the email
	         e.printStackTrace();
		}
		 
//       try {
//       MimeMessage mail = mailSender.createMimeMessage();
//       MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//		helper.setTo(emailAddress);
         //mailSender.send(message);
//     } catch (MessagingException e) {
//         // Handle any exceptions related to sending the email
//         e.printStackTrace();
//     }
    }
	
	private SimpleMailMessage constructResetTokenEmail(
		  String contextPath, Locale locale, PasswordResetTokenDto resetTokenDto, UserDto user) {
		  String url = contextPath + "?token=" + resetTokenDto.getToken();
		  String message = "To reset your password, click the link below:";
		  long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), resetTokenDto.getExpiryDate());
		  minutes = minutes + 1;
		  String extra = "This link will expire in " + minutes + "  minutes.";
		  return constructEmail("Blog App Reset Password", message + " \r\n" + url + " \r\n\n" + extra, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, 
	  UserDto user) {
		
	    SimpleMailMessage mail = new SimpleMailMessage();
	    mail.setSubject(subject);
	    mail.setText(body);
	    mail.setTo(user.getEmail());
//	    System.out.println(mail.getTo());
	    
	    //mail.setFrom(fromMail);//if it is not used spring.mail.username specified in JavaMailSender config or properties will be used
	    System.out.println(mail.getFrom());
	    //email.setFrom(env.getProperty("support.email")); //using env variable is a better approach to hide the email and its password
	    //but for hard coded in application properties will do the job
	    return mail;
	}
	
}
