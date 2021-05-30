package br.com.unamaproject.server.service;

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
import br.com.unamaproject.server.dto.LaboratorioDTO;
import br.com.unamaproject.server.dto.LaboratorioNewDTO;
import br.com.unamaproject.server.repositories.LaboratorioRepository;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class LaboratorioService {

	@Autowired
	private LaboratorioRepository repository;
	
	public Laboratorio findById(Integer id) {
		Optional<Laboratorio> obj = repository.findById(id);
		Laboratorio lab = obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Laboratorio.class.getName()));
		calcAvg(lab);
		return lab;
	}

	@Transactional
	public Laboratorio insert(LaboratorioNewDTO objDto) {
		Laboratorio obj = fromNewDTO(objDto);
		return repository.save(obj);
	}

	public Laboratorio update(Laboratorio obj) {
		return repository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		repository.deleteById(id);
	}

	public List<Laboratorio> findAll() {
		List<Laboratorio> list = repository.findAll();
		generateAvg(list);
		return list;
	}

	public Page<Laboratorio> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		Page<Laboratorio> pageList = repository.findAll(pageRequest);
		for (Laboratorio laboratorio : pageList) calcAvg(laboratorio);
		return pageList;
	}
	
	private Laboratorio fromNewDTO(LaboratorioNewDTO objDto) {
		return new Laboratorio(null, objDto.getNome(), objDto.getImgUrl(), objDto.getDescricao());
	}

	public Laboratorio fromDTO(LaboratorioDTO objDto) {
		Laboratorio updateObj = findById(objDto.getId());
		updateObj.setNome(objDto.getNome());
		updateObj.setImgUrl(objDto.getImgUrl());
		updateObj.setDescricao(objDto.getDescricao());
		return updateObj;
	}
	
	private void generateAvg(List<Laboratorio> list) {
		for (Laboratorio laboratorio : list) {
			calcAvg(laboratorio);
		}
	}
	
	private void calcAvg(Laboratorio obj) {
		int sum = obj.getAvaliacoes().isEmpty() ? 1 : 0;
		for (Avaliacao av : obj.getAvaliacoes()) {
			sum += av.getQtdEstrelas();
		}
		obj.setAvgAvaliacoes((double) sum / obj.getAvaliacoes().size());
	}
} 
