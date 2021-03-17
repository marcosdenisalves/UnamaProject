package br.com.unamaproject.server.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.repositories.LaboratorioRepository;

@Service
public class LaboratorioService {

	@Autowired
	private LaboratorioRepository repository;
	
	public Laboratorio findById(Integer id) {
		Optional<Laboratorio> obj = repository.findById(id);
		return obj.orElse(null);
	}
} 
