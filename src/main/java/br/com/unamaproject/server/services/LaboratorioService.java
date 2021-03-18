package br.com.unamaproject.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.repositories.LaboratorioRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class LaboratorioService {

	@Autowired
	private LaboratorioRepository repository;
	
	public Laboratorio findById(Integer id) {
		Optional<Laboratorio> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Laboratorio.class.getName()));
	}
} 
