package com.example.email.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.email.model.EmailModel;
import com.example.email.model.StatusEmail;
import com.example.email.repository.EmailRepository;

@Service
public class EmailService {
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private JavaMailSender emailSender;
	
	@SuppressWarnings("finally")
	public EmailModel sendEmail(EmailModel emailModel) {
		emailModel.setDataEnvio(LocalDateTime.now());
		
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(emailModel.getDe());
			message.setTo(emailModel.getPara());
			message.setSubject(emailModel.getTitulo());
			message.setText(emailModel.getTexto());

			emailSender.send(message);
			emailModel.setStatusEmail(StatusEmail.Enviado);
			
		}catch (MailException e) {
			emailModel.setStatusEmail(StatusEmail.Error);
		}finally {
			return emailRepository.save(emailModel);
		}
	}
}
