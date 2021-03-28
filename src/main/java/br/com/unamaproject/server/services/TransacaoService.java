package br.com.unamaproject.server.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unamaproject.server.domain.Transacao;
import br.com.unamaproject.server.dto.TransacaoNewDTO;
import br.com.unamaproject.server.repositories.TransacaoRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;
import br.com.unamaproject.server.utils.DateUtils;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;
	
	public Transacao findById(Integer id) {
		Optional<Transacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Transacao.class.getName()));
	}

	@Transactional
	public Transacao insert(TransacaoNewDTO objDto) {
		Transacao obj = fromDTO(objDto);
		return repository.save(obj);
	}

	public Transacao update(Transacao obj) {
		findById(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	public List<Transacao> findAll() {
		return repository.findAll();
	}

	public Page<Transacao> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	private Transacao fromDTO(TransacaoNewDTO objDto) {
		Date date = DateUtils.convertStringtoDate(objDto.getDate());
		return new Transacao(null, objDto.getTitle(), objDto.getValue(), date);
	}
} 
