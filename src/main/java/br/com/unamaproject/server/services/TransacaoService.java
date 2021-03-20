package br.com.unamaproject.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Transacao;
import br.com.unamaproject.server.repositories.TransacaoRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class TransacaoService {

	@Autowired
	private TransacaoRepository repository;
	
	public Transacao findById(Integer id) {
		Optional<Transacao> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Transacao.class.getName()));
	}
} 
