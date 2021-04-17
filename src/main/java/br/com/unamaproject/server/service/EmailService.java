package br.com.unamaproject.server.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.unamaproject.server.domain.Usuario;

public interface EmailService {

	void sendRegisterConfirmationEmail(Usuario usuario);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendRegisterConfirmationHtmlEmail(Usuario usuario);
	
	void sendHtmlEmail(MimeMessage msg);
}
