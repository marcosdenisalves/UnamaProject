package br.com.unamaproject.server.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import br.com.unamaproject.server.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendRegisterConfirmationEmail(Usuario obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Usuario obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Usuário cadastrado com sucesso!");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(subjectModelRegister(obj));
		return sm;
	}
	
	private String subjectModelRegister(Usuario obj) {
		StringBuilder sb = new StringBuilder();
		sb.append("Olá ");
		sb.append(obj.getNome());
		sb.append(", ");
		sb.append("seu cadastro foi efetuado com sucesso!");
		sb.append(" ");
		sb.append("Clique no link abaixo para efetuar o seu login. ");
		sb.append("https://unamavr.netlify.app/#/login");
		return sb.toString();
	}
}
