package br.com.unamaproject.server.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.dto.AvaliacaoDTO;
import br.com.unamaproject.server.dto.AvaliacaoNewDTO;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.resources.UserService;
import br.com.unamaproject.server.security.UserSS;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public Avaliacao findById(Integer id) {
		Optional<Avaliacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Avaliacao.class.getName()));
	}


	@Transactional
	public Avaliacao insert(AvaliacaoNewDTO objDto) {
		Avaliacao obj = fromNewDTO(objDto);
		return repository.save(obj);
	}

	public Avaliacao update(Avaliacao obj) {
		validaUsuarioLogado();
		findById(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		validaUsuarioLogado();
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
		Usuario usuario = validaUsuarioLogado();
		return new Avaliacao(null, objDto.getQtdEstrelas(), objDto.getComentario(), new Date(), usuario);
	}

	public Avaliacao fromDTO(AvaliacaoDTO objDto) {
		Usuario usuario = validaUsuarioLogado();
		return new Avaliacao(objDto.getId(), objDto.getQtdEstrelas(), objDto.getComentario(), new Date(), usuario);
	}
	
	private Usuario validaUsuarioLogado() {
		UserSS user = UserService.authenticated();
		return usuarioService.findById(user.getId());
	}
} 