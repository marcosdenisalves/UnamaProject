package br.com.unamaproject.server.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.repositories.UsuarioRepository;
import br.com.unamaproject.server.resources.exception.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, Usuario> {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public void initialize(UsuarioInsert ann) {
	}
	
	@Override
	public boolean isValid(Usuario usuario, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		Usuario aux = repository.findByEmail(usuario.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email" ,"Email já exitente"));
		}
		
		for (FieldMessage e :list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
				e.getMessage()).addPropertyNode(e.getFieldName())
				.addConstraintViolation();
		}
			
		return list.isEmpty();
	}
}
