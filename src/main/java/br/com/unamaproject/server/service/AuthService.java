package br.com.unamaproject.server.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.repositories.UsuarioRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random rand = new Random(); 	
	
	public void sendNewPassword(String email) {
		Usuario user = usuarioRepository.findByEmail(email);
		if (user == null)
			throw new ObjectNotFoundException("Email não encontrado");
		
		String newPass = newPassword();
		user.setSenha(encoder.encode(newPass));
		
		usuarioRepository.save(user);
		emailService.sendNewPasswordEmail(user, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < vet.length; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) //gera um digito aleatorio de 0 a 9
			return (char) (rand.nextInt(10) + 48);
		else if (opt == 1) //gera uma letra maiuscula aleatoria de A a Z
			return (char) (rand.nextInt(26) + 65);
		else //gera uma letra minuscula aleatoria de A a Z
			return (char) (rand.nextInt(26) + 97);
	}
}
