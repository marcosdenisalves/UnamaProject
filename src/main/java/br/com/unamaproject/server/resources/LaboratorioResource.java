package br.com.unamaproject.server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.services.LaboratorioService;

@RestController
@RequestMapping(value = "/laboratorios")
public class LaboratorioResource {

	@Autowired
	private LaboratorioService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Laboratorio> listar(@PathVariable Integer id) {
		Laboratorio obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
