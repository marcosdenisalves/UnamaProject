package br.com.unamaproject.server.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.unamaproject.server.domain.Laboratorio;
import br.com.unamaproject.server.dto.LaboratorioDTO;
import br.com.unamaproject.server.dto.LaboratorioNewDTO;
import br.com.unamaproject.server.service.LaboratorioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/laboratorios")
@Api(value = "API REST Laboratorios")
public class LaboratorioResource {

	@Autowired
	private LaboratorioService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um laboratorio passando um id. {Exemplo: laboratorios/id}")
	public ResponseEntity<Laboratorio> listar(@PathVariable Integer id) {
		Laboratorio obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Inseri um laboratorios passando suas informações no body")
	public ResponseEntity<Void> insert(@Valid @RequestBody LaboratorioNewDTO objDto) {
		Laboratorio obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza um laboratorios passando o seu id e os campos desejado no body. {Exemplo: laboratorios/id}")
	public ResponseEntity<Void> update(@Valid @RequestBody LaboratorioDTO objDto, @PathVariable Integer id) {
		Laboratorio obj = service.fromDTO(objDto);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deleta um laboratorio passando o seu id {Exemplo: laboratorios/id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Traz todos os laboratorios registrados")
	public ResponseEntity<List<Laboratorio>> findAll() {
		List<Laboratorio> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ApiOperation(value = "Traz todos os laboratorios registrados de forma paginada. {Exemplo: laboratorios/page?linesPerPage=3&page=1&direction=DESC}")
	public ResponseEntity<Page<Laboratorio>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")	Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")	String direction) {
		
		Page<Laboratorio> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
