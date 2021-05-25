package br.com.unamaproject.server.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.dto.AvaliacaoDTO;
import br.com.unamaproject.server.dto.AvaliacaoNewDTO;
import br.com.unamaproject.server.enums.PerfilAcesso;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.security.UserSS;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;
import br.com.unamaproject.server.service.exceptions.OperationNotAllowedException;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private LaboratorioService laboratorioService;
	
	public Avaliacao findById(Integer id) {
		Optional<Avaliacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Avaliacao.class.getName()));
	}

	@Transactional
	public Avaliacao insert(AvaliacaoNewDTO objDto) {
		Avaliacao obj = validaAvaliacoesDoUsuario(fromNewDTO(objDto));
		generateAvg(obj);
		return repository.save(obj);
	}

	public Avaliacao update(Avaliacao obj) {
		generateAvg(obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	public List<Avaliacao> findAll() {
		return repository.findAll();
	}

	public Page<Avaliacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Avaliacao fromNewDTO(AvaliacaoNewDTO objDto) {
		Usuario usuario = userValidates();
		Laboratorio laboratorio = laboratorioService.findById(objDto.getIdLaboratorio());
		return new Avaliacao(null, objDto.getQtdEstrelas(), objDto.getComentario(), LocalDateTime.now(), usuario, laboratorio);
	}

	public Avaliacao fromDTO(AvaliacaoDTO objDto) {
		Usuario usuario = userValidates();
		if (usuario.getPerfis().contains(PerfilAcesso.ADMIN)) {
			usuario = findById(objDto.getId()).getUsuario();
		}
		return new Avaliacao(objDto.getId(), objDto.getQtdEstrelas(), objDto.getComentario(),
				LocalDateTime.now(), usuario, findById(objDto.getId()).getLaboratorio());
	}
	
	private Usuario userValidates() {
		UserSS user = UserService.authenticated();
		return usuarioService.findById(user.getId());
	}

	public void generateAvg(Avaliacao avaliacao) {
		Laboratorio laboratorio = avaliacao.getLaboratorio();
		laboratorio.getAvaliacoes().forEach((x) -> {
			if (x.getId() == avaliacao.getId())
				x.setQtdEstrelas(avaliacao.getQtdEstrelas());
		});
		
		int length = laboratorio.getAvaliacoes().size();
		int sum = laboratorio.getAvaliacoes().stream().reduce(0,
				(acc, x) -> acc + x.getQtdEstrelas(), Integer::sum);
		
		if (avaliacao.getId() == null) {
			sum += avaliacao.getQtdEstrelas();
			length += 1;
		}
		
		laboratorio.setAvgAvaliacoes((double) sum / length);
	}

	private Avaliacao validaAvaliacoesDoUsuario(Avaliacao obj) {
		Laboratorio laboratorio = laboratorioService.findById(obj.getLaboratorio().getId());
		for (Avaliacao avaliacao : laboratorio.getAvaliacoes()) {
			if (avaliacao.getUsuario().getId() == UserService.authenticated().getId()) {
				throw new OperationNotAllowedException("Já existe comentários deste usuário neste laboratório");
			}
		}
		return obj;
	}
} 

