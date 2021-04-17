package br.com.unamaproject.server.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public void instantiateTestDatabase() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Laboratorio lab1 = new Laboratorio(null, "Informática", "Laborátorio de Informática da Unama");
		Laboratorio lab2 = new Laboratorio(null, "Química", "Laborátorio de Química da Unama");
		Laboratorio lab3 = new Laboratorio(null, "Gastronomia", "Laborátorio de Gastronomia da Unama");

		Usuario user1 = new Usuario(null, "Daywison", "da Silva de Souza", "daywison.silva@gmail.com", encoder.encode("1234"));
		Usuario user2 = new Usuario(null, "Paulo Roberto", "de Souza Soares", "pauloroberto.souza@yahoo.com.br", encoder.encode("4444"));
		Usuario user3 = new Usuario(null, "Thiago", "de Oliveira Gomes", "thiagodeoliveira@hotmail.com", encoder.encode("2222"));
		Usuario user4 = new Usuario(null, "Marcos Dênis", "Alves Lucio Junior", "marcos.denis@hotmail.com", encoder.encode("4321"));
		Usuario user5 = new Usuario(null, "Carlos André", "Pereira Alves", "carlos.alves@hotmail.com", encoder.encode("55555"));
		Usuario user6 = new Usuario(null, "Fulano", "Sicrano de tal", "fulano.sicrano@yahoo.com.br", encoder.encode("3333"));
		Usuario adm = new Usuario(null, "Administrador", "Adm", "administrador@adm.com", encoder.encode("adm"));
		
		Avaliacao av1 = new Avaliacao(null, 2, "Não gostei do conteudo", sdf.parse("13/11/2019"), user1);
		Avaliacao av2 = new Avaliacao(null, 5, "Excelente projeto", sdf.parse("15/09/2020"), user1);
		Avaliacao av3 = new Avaliacao(null, 3, "Acredito que o conteúdo aprensentado poderia ser melhor", sdf.parse("08/02/2021"), user1);
		
		Avaliacao av4 = new Avaliacao(null, 5, "Excelente conteudo", sdf.parse("01/05/2015"), user2);
		Avaliacao av5 = new Avaliacao(null, 4, "Otimo trabalho galera", sdf.parse("02/07/2016"), user2);
		Avaliacao av6 = new Avaliacao(null, 3, "Precisa melhorar", sdf.parse("13/12/2021"), user2);
		
		Avaliacao av7 = new Avaliacao(null, 2, "Não ficou muito bom", sdf.parse("05/01/2020"), user3);
		Avaliacao av8 = new Avaliacao(null, 5, "Excelente desenvolvimento e conclusão", sdf.parse("04/02/2018"), user3);
		Avaliacao av9 = new Avaliacao(null, 3, "Boa galera, gostei demais!", sdf.parse("01/10/2021"), user3);
		
		adm.addPerfil(PerfilAcesso.ADMIN);
		user1.addPerfil(PerfilAcesso.USUARIO);
		
		user1.getAvaliacoes().addAll(Arrays.asList(av1, av2, av3));
		user2.getAvaliacoes().addAll(Arrays.asList(av4, av5, av6));
		user3.getAvaliacoes().addAll(Arrays.asList(av7, av8, av9));
		
		laboratorioRepository.saveAll(Arrays.asList(lab1, lab2, lab3));
		usuarioRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6, adm));
		avaliacaoRepository.saveAll(Arrays.asList(av1, av2, av3, av4, av5, av6, av7, av8, av9));
	}
}