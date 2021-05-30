package br.com.unamaproject.server.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.enums.PerfilAcesso;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.repositories.LaboratorioRepository;
import br.com.unamaproject.server.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private LaboratorioRepository laboratorioRepository;
		
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instantiateTestDatabase() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Laboratorio lab1 = new Laboratorio(null, "Biblioteca", "img/biblioteca.png","Biblioteca da Unama");
		Laboratorio lab2 = new Laboratorio(null, "Química", "img/lab-quimica.png", "Laborátorio de Química");
		Laboratorio lab3 = new Laboratorio(null, "Gastronomia", "img/lab-106.png", "Laborátorio de Gastronomia");
//		Laboratorio lab4 = new Laboratorio(null, "Engenharia Civil", "img/lab-egn-civil.png", "Laborátorio de Engenharia Civil");
//		Laboratorio lab5 = new Laboratorio(null, "Moda", "img/lab-moda.png", "Laborátorio de Moda");
//		Laboratorio lab6 = new Laboratorio(null, "Cam", "img/lacam.png", "Laborátorio de cam");
//		Laboratorio lab7 = new Laboratorio(null, "102", "img/lab-102.png", "Laborátorio de 102");

		Usuario user1 = new Usuario(null, "Daywison", "da Silva de Souza", "daywison.silva@gmail.com", encoder.encode("1234"));
		Usuario user2 = new Usuario(null, "Paulo Roberto", "de Souza Soares", "pauloroberto.souza@yahoo.com.br", encoder.encode("4444"));
		Usuario user3 = new Usuario(null, "Thiago", "de Oliveira Gomes", "thiagodeoliveira@hotmail.com", encoder.encode("2222"));
		Usuario adm = new Usuario(null, "Administrador", "Adm", "administrador@adm.com", encoder.encode("adm"));
		
		Avaliacao av1 = new Avaliacao(null, 2, "Não gostei do conteudo", LocalDate.parse("13/11/2019", formatter).atStartOfDay(), user1);
		Avaliacao av2 = new Avaliacao(null, 5, "Excelente conteudo", LocalDate.parse("01/05/2015", formatter).atStartOfDay(), user2);
		Avaliacao av3 = new Avaliacao(null, 3, "Boa galera, gostei demais!", LocalDate.parse("01/10/2021", formatter).atStartOfDay(), user3);
				
		adm.addPerfil(PerfilAcesso.ADMIN);
		user1.addPerfil(PerfilAcesso.USUARIO);
		user2.addPerfil(PerfilAcesso.USUARIO);
		user3.addPerfil(PerfilAcesso.USUARIO);
		
		user1.getAvaliacoes().addAll(Arrays.asList(av1));
		user2.getAvaliacoes().addAll(Arrays.asList(av2));
		user3.getAvaliacoes().addAll(Arrays.asList(av3));

		avaliacaoRepository.saveAll(Arrays.asList(av1, av2, av3));
		
		lab1.getAvaliacoes().addAll(Arrays.asList(av1, av2, av3));
		lab2.getAvaliacoes().addAll(Arrays.asList(av1, av2, av3));
		lab3.getAvaliacoes().addAll(Arrays.asList(av1, av2, av3));
		
		av1.getLaboratorios().addAll(Arrays.asList(lab1, lab2, lab3));
		av2.getLaboratorios().addAll(Arrays.asList(lab1, lab2, lab3));
		av3.getLaboratorios().addAll(Arrays.asList(lab1, lab2, lab3));
		
		laboratorioRepository.saveAll(Arrays.asList(lab1, lab2, lab3));
		usuarioRepository.saveAll(Arrays.asList(adm, user1, user2, user3));
	}
}
