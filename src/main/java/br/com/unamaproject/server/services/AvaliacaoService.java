package br.com.unamaproject.server.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.repositories.AvaliacaoRepository;
import br.com.unamaproject.server.service.exceptions.DataIntegrityException;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class AvaliacaoService {

	@Autowired
	private AvaliacaoRepository repository;
	
	public Avaliacao findById(Integer id) {
		Optional<Avaliacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Avaliacao.class.getName()));
	}

	public Avaliacao insert(Avaliacao obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Avaliacao update(Avaliacao obj) {
		findById(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		}catch (DataIntegrityException e) {
			throw new DataIntegrityException
				("Não foi possivel remover a " + Avaliacao.class + 
						", a entidade contem usuários atrelados a ela.");
		}
	}

	public List<Avaliacao> findAll() {
		return repository.findAll();
	}

	public Page<Avaliacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
} 
