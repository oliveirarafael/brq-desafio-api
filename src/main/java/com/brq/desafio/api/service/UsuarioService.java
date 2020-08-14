package com.brq.desafio.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.brq.desafio.api.controller.advice.exception.ConflictException;
import com.brq.desafio.api.controller.advice.exception.NotFoundException;
import com.brq.desafio.api.model.entity.Usuario;
import com.brq.desafio.api.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> getUsuarios() {
		return usuarioRepository.findAll();
	}

	public List<Usuario> getUsuarioPorEmail(String email) {
		List<Usuario> usuarios = usuarioRepository.findByEmail(email);

		if (usuarios.isEmpty()) {
			throw new NotFoundException("Usuário não foi encontrado");
		}
		
		return usuarios;
	}
	
	public Usuario getUsuarioPorId(Long id) {
		Optional<Usuario> usuarioConsultado = usuarioRepository.findById(id);
		
		if (usuarioConsultado.isPresent()) {
			return usuarioConsultado.get();
		}
		
		throw new NotFoundException("Usuário não foi encontrado");
	}
	
	public List<Usuario> getUsuarioPorCompanyId(Long id){
		List<Usuario> usuarios = usuarioRepository.findByCompanyId(id);
		
		if (usuarios.isEmpty()) {
			throw new NotFoundException("Usuário não foi encontrado");
		}
		
		return usuarios;
	}

	public Usuario salvar(Usuario usuario) {
		List<Usuario> usuarios = usuarioRepository.findByEmail(usuario.getEmail());
		long quantidadeUsuarios = usuarios.stream().filter(u -> u.getCompanyId().equals(usuario.getCompanyId())).count();
		
		if(quantidadeUsuarios == 0L)
		   return usuarioRepository.save(usuario);
	    
		throw new ConflictException("Usuário "+usuario.getEmail()+" já cadastrado nesta Companhia");
	}

	public void deletar(Long id) {
		Usuario usuario = getUsuarioPorId(id);
		usuarioRepository.delete(usuario);
	}
	
	public Usuario atualizar(Long id, Usuario usuario) {
		List<Usuario> usuarios = usuarioRepository.findByEmail(usuario.getEmail());
		long quantidadeUsuarios = usuarios.stream().filter(u -> u.getCompanyId().equals(usuario.getCompanyId())).count();
		
		if(quantidadeUsuarios == 0L) {
			Usuario usuarioConsultado = getUsuarioPorId(id);
			usuarioConsultado.setEmail(usuario.getEmail());
			usuarioConsultado.setBirthDate(usuario.getBirthDate());
			usuarioConsultado.setCompanyId(usuario.getCompanyId());
		
		    return usuarioConsultado;
		}
		
		throw new ConflictException("Usuário "+usuario.getEmail()+" já cadastrado nesta Companhia");
	}

	
}
