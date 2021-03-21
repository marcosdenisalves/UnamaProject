package br.com.unamaproject.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.repositories.UsuarioRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	
	public Usuario findById(Integer id) {
		Optional<Usuario> obj = usuarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	public Usuario insert(Usuario usuario) {
		usuario.setId(null);
		return usuarioRepository.save(usuario);
	}

	public Usuario update(Usuario obj) {
		findById(obj.getId());
		return usuarioRepository.save(obj);
	}

	public void delete(Integer id) {
		Usuario usuario = findById(id);
		if (!usuario.getAvaliacoes().isEmpty())
			for (Avaliacao avaliacao : usuario.getAvaliacoes()) {
				avaliacaoRepository.deleteById(avaliacao.getId());
			}

		usuarioRepository.deleteById(id);
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return usuarioRepository.findAll(pageRequest);
	}
} 
