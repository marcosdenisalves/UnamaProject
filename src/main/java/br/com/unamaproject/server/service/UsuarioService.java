package br.com.unamaproject.server.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unamaproject.server.domain.Usuario;
import br.com.unamaproject.server.dto.UsuarioDTO;
import br.com.unamaproject.server.dto.UsuarioNewDTO;
import br.com.unamaproject.server.enums.PerfilAcesso;
import br.com.unamaproject.server.repositories.UsuarioRepository;
import br.com.unamaproject.server.security.UserSS;
import br.com.unamaproject.server.service.exceptions.AuthorizationException;
import br.com.unamaproject.server.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public Usuario findById(Integer id) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(PerfilAcesso.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		Optional<Usuario> obj = usuarioRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				 "Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName()));
	}

	@Transactional
	public Usuario insert(UsuarioNewDTO objDto) {
		Usuario obj = fromNewDTO(objDto);
		emailService.sendRegisterConfirmationHtmlEmail(obj);
		return usuarioRepository.save(obj);
	}
	
	public Usuario update(Usuario obj) {
		findById(obj.getId());
		return usuarioRepository.save(obj);
	}

	public void delete(Integer id) {
		findById(id);
		usuarioRepository.deleteById(id);
	}

	public List<Usuario> findAll() {
		Usuario user = findById(UserService.authenticated().getId());
		if (UserService.authenticated().hasRole(PerfilAcesso.ADMIN))
			return usuarioRepository.findAll();
		
		return Arrays.asList(user);
	}
	
	public Usuario findByEmail(String email) {
		UserSS user = UserService.authenticated();
		if (user == null || !user.hasRole(PerfilAcesso.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Usuario obj = usuarioRepository.findByEmail(email);
		if (obj == null)
			throw new ObjectNotFoundException(
					 "Objeto não encontrado! Id: " + user.getId() + 
					 ", Tipo: " + Usuario.class.getName());
		return obj;
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction), orderBy);
		return usuarioRepository.findAll(pageRequest);
	}
	
	public Usuario fromDTO(UsuarioDTO objDto) {
		return new Usuario(objDto.getId(), objDto.getNome(), objDto.getSobrenome(), objDto.getEmail(), null);
	}
	
	public Usuario fromNewDTO(UsuarioNewDTO objDto) {
		return new Usuario(null, objDto.getNome(), objDto.getSobrenome(), objDto.getEmail(), encoder.encode(objDto.getSenha()));
	}
} 