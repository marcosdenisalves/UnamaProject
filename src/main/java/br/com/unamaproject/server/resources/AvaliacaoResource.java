package br.com.unamaproject.server.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.unamaproject.server.domain.Avaliacao;
import br.com.unamaproject.server.services.AvaliacaoService;

@RestController
@RequestMapping(value = "/avaliacoes")
public class AvaliacaoResource {

	@Autowired
	private AvaliacaoService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Avaliacao> listar(@PathVariable Integer id) {
		Avaliacao obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
