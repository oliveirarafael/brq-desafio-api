package com.brq.desafio.api.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.brq.desafio.api.model.entity.Usuario;
import com.brq.desafio.api.service.UsuarioService;
import com.brq.desafio.api.view.UsuarioView;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<List<Usuario>> getUsuarios() {
		return ResponseEntity.ok(usuarioService.getUsuarios());
	}
	
	@GetMapping("/email/{email}")
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<List<Usuario>> getUsuarioPorEmail(@PathVariable String email) {
		return ResponseEntity.ok(usuarioService.getUsuarioPorEmail(email));
	}

	@GetMapping("/{id}")
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.getUsuarioPorId(id));
	}

	@GetMapping("/companies/{id}")
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<List<Usuario>> getUsuarioPorCompany(@PathVariable Long id) {
		return ResponseEntity.ok(usuarioService.getUsuarioPorCompanyId(id));
	}

	@PostMapping
	@Transactional
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<Usuario> salvar(
			@RequestBody @Valid @JsonView(value = UsuarioView.FORM.Post.class) Usuario usuario,
			UriComponentsBuilder uriBuilder) {
		Usuario usuarioCadastrado = usuarioService.salvar(usuario);
		URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuarioCadastrado.getUserId()).toUri();
		return ResponseEntity.created(uri).body(usuarioCadastrado);
	}

	@PutMapping("/{id}")
	@Transactional
	@JsonView(value = UsuarioView.DTO.class)
	public ResponseEntity<Usuario> put(@PathVariable Long id, 
			                           @RequestBody @Valid @JsonView(value = UsuarioView.FORM.Put.class) Usuario usuario) {
		
       return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deletar(@PathVariable Long id) {
		usuarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
