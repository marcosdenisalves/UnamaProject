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

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.dto.AvaliacaoDTO;
import br.com.unamaproject.server.dto.AvaliacaoNewDTO;
import br.com.unamaproject.server.service.AvaliacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/avaliacoes")
@Api(value = "API REST Avaliacoes")
public class AvaliacaoResource {

	@Autowired
	private AvaliacaoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna uma Avaliação passando um id. {Exemplo: avaliacoes/id}")
	public ResponseEntity<Avaliacao> listar(@PathVariable Integer id) {
		Avaliacao obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Inseri uma avaliação passando suas informações no body")
	public ResponseEntity<Void> insert(@Valid @RequestBody AvaliacaoNewDTO objDto) {
		Avaliacao obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza uma avaliação passando o seu id e os campos desejado no body. {Exemplo: avaliacoes/id}")
	public ResponseEntity<Void> update(@Valid @RequestBody AvaliacaoDTO objDto, @PathVariable Integer id) {
		Avaliacao obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deleta uma avaliação passando o seu id, {Exemplo: avaliacoes/id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Traz todos as avaliações registradas. ")
	public ResponseEntity<List<Avaliacao>> findAll() {
		List<Avaliacao> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ApiOperation(value = "Traz todas as avaliações registradas de forma paginada. {Exemplo: avaliacoes/page?linesPerPage=3&page=1&direction=DESC}")
	public ResponseEntity<Page<Avaliacao>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")	Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")	String direction) {
		
		Page<Avaliacao> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}
