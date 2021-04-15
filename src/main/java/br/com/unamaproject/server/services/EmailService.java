package br.com.unamaproject.server.services;

import org.springframework.mail.SimpleMailMessage;

import br.com.unamaproject.server.domain.Usuario;

public interface EmailService {

	void sendRegisterConfirmationEmail(Usuario usuario);
	
	void sendEmail(SimpleMailMessage msg);
}
