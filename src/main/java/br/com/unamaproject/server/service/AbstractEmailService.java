package br.com.unamaproject.server.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.unamaproject.server.domain.Usuario;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMeailSender;
	
	@Override
	public void sendRegisterConfirmationEmail(Usuario obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromUsuario(obj);
		sendEmail(sm);
	}
	
	@Override
	public void sendRegisterConfirmationHtmlEmail(Usuario usuario ) {
		
		try {
			MimeMessage mm = prepareMimeMessageFromUsuario(usuario);
			sendHtmlEmail(mm);
		}
		catch (MessagingException e) {
			sendRegisterConfirmationEmail(usuario);
		}
	}

	protected MimeMessage prepareMimeMessageFromUsuario(Usuario usuario) throws MessagingException {
		MimeMessage mimeMessage = javaMeailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(usuario.getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Usuário cadastrado com sucesso!");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplateRegister(usuario), true);
		return mimeMessage;
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromUsuario(Usuario obj) {
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
		sb.append("o seu cadastro foi efetuado com sucesso!");
		sb.append(" ");
		sb.append("Clique no link abaixo para efetuar o seu login. ");
		sb.append("https://unamavr.netlify.app/#/login");
		return sb.toString();
	}
	
	protected String htmlFromTemplateRegister(Usuario usuario) {
		Context context = new Context();
		context.setVariable("usuario", usuario);
		return templateEngine.process("email/confirmacaoCadastro", context);
	}
	
	@Override
	public void sendNewPasswordEmail(Usuario usuario, String newPasss) {
		SimpleMailMessage sm = prepareNewPasswordEmail(usuario, newPasss);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareNewPasswordEmail(Usuario usuario, String newPasss) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(usuario.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha!");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPasss);
		return sm;
	}
}
