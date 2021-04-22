package br.com.unamaproject.server.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.dto.UsuarioDTO;
import br.com.unamaproject.server.dto.UsuarioNewDTO;
import br.com.unamaproject.server.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/usuarios")
@Api(value = "API REST Usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um usuário passando um id. {Exemplo: usuarios/id}")
	public ResponseEntity<Usuario> listar(@PathVariable Integer id) {
		Usuario obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/email", method = RequestMethod.GET)
	@ApiOperation(value = "Retorna um usuário passando um email. {Exemplo: usuarios/email}")
	public ResponseEntity<Usuario> findByEmail(@RequestParam(value = "value") String email) {
		Usuario obj = service.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Inseri um usuário passando suas informações no body")
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioNewDTO objDto) {
		Usuario obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Atualiza um usuário passando o seu id e os campos desejado no body. {Exemplo: usuarios/id}")
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDTO objDto, @PathVariable Integer id) {
		Usuario obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deleta um usuário passando o seu id, ('Disponível apenas para administradores') {Exemplo: usuarios/id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "(Perfil de ADM) Traz todos os usuários registrados, (Perfil de Usuário) Traz apenas o usuário da sessão")
	public ResponseEntity<List<Usuario>> findAll() {
		List<Usuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "Traz todos os usuários registrados. (Apenas ADM's) {Exemplo: usuarios/page?linesPerPage=3&page=1&direction=DESC}")
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<Usuario>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24")	Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC")	String direction) {
		
		Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}