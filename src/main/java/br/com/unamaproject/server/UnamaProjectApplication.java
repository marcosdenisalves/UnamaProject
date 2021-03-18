package br.com.unamaproject.server;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.repositories.LaboratorioRepository;
import br.com.unamaproject.server.repositories.UsuarioRepository;

@SpringBootApplication
public class UnamaProjectApplication implements CommandLineRunner{

	@Autowired
	private LaboratorioRepository laboratorioRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(UnamaProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Laboratorio lab1 = new Laboratorio(null, "Informática", "Laborátorio de Informática da Unama");
		Laboratorio lab2 = new Laboratorio(null, "Química", "Laborátorio de Química da Unama");
		Laboratorio lab3 = new Laboratorio(null, "Gastronomia", "Laborátorio de Gastronomia da Unama");

		Usuario user1 = new Usuario(null, "Daywison", "da Silva de Souza", "daywison.silva@gmail.com", "1234");
		Usuario user2 = new Usuario(null, "Paulo Roberto", "de Souza Soares", "pauloroberto.souza@yahoo.com.br", "12345");
		Usuario user3 = new Usuario(null, "Thiago", "de Oliveira Gomes", "thiagodeoliveira@hotmail.com", "2222");
		
		Avaliacao av1 = new Avaliacao(null, 2, "Não gostei do conteudo", LocalDate.parse("13/11/2019", formatter), user1);
		Avaliacao av2 = new Avaliacao(null, 5, "Excelente projeto", LocalDate.parse("15/09/2020", formatter), user1);
		Avaliacao av3 = new Avaliacao(null, 3, "Acredito que o conteúdo aprensentado poderia ser melhor", LocalDate.parse("08/02/2021", formatter), user1);
		
		Avaliacao av4 = new Avaliacao(null, 5, "Excelente conteudo", LocalDate.parse("01/05/2015", formatter), user2);
		Avaliacao av5 = new Avaliacao(null, 4, "Otimo trabalho galera", LocalDate.parse("02/07/2016", formatter), user2);
		Avaliacao av6 = new Avaliacao(null, 3, "Precisa melhorar", LocalDate.parse("13/12/2021", formatter), user2);
		
		Avaliacao av7 = new Avaliacao(null, 2, "Não ficou muito bom", LocalDate.parse("05/01/2020", formatter), user3);
		Avaliacao av8 = new Avaliacao(null, 5, "Excelente desenvolvimento e conclusão", LocalDate.parse("04/02/2018", formatter), user3);
		Avaliacao av9 = new Avaliacao(null, 3, "Boa galera, gostei demais!", LocalDate.parse("01/10/2021", formatter), user3);
		
		
		user1.getAvaliacoes().addAll(Arrays.asList(av1, av2, av3));
		user2.getAvaliacoes().addAll(Arrays.asList(av4, av5, av6));
		user3.getAvaliacoes().addAll(Arrays.asList(av7, av8, av9));
		
		
		laboratorioRepository.saveAll(Arrays.asList(lab1, lab2, lab3));
		usuarioRepository.saveAll(Arrays.asList(user1, user2, user3));
		avaliacaoRepository.saveAll(Arrays.asList(av1, av2, av3, av4, av5, av6, av7, av8, av9));
	}
}
