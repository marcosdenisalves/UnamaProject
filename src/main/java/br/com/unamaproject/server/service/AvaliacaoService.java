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
		Avaliacao obj = fromNewDTO(objDto);
		validaAvaliacoesDoUsuario(obj);
		return repository.save(obj);
	}

	public Avaliacao update(Avaliacao obj) {
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
		Avaliacao avaliacao = new Avaliacao(null, objDto.getQtdEstrelas(), objDto.getComentario(), LocalDateTime.now(), usuario);
		Laboratorio laboratorio = laboratorioService.findById(objDto.getIdLaboratorio());
		laboratorio.getAvaliacoes().add(avaliacao);
		avaliacao.getLaboratorios().add(laboratorio);
		return avaliacao;
	}

	public Avaliacao fromDTO(AvaliacaoDTO objDto) {
		Avaliacao avaliacao = findById(objDto.getId());
		avaliacao.setQtdEstrelas(objDto.getQtdEstrelas());
		avaliacao.setComentario(objDto.getComentario());
		avaliacao.setDataAvaliacao(LocalDateTime.now());
		return avaliacao;
	}
	
	private Usuario userValidates() {
		UserSS user = UserService.authenticated();
		return usuarioService.findById(user.getId());
	}

	private void validaAvaliacoesDoUsuario(Avaliacao avaliacao) {
		for (Laboratorio laboratorio : avaliacao.getLaboratorios()) {
			for (Avaliacao av : laboratorio.getAvaliacoes()) {
				if (av.getId() != null && av.getUsuario().getId() == UserService.authenticated().getId()) {
					throw new OperationNotAllowedException("Não foi possível adicionar a sua avaliação, apenas uma por laborátorio");
				}
			}
		}
	}
} 

