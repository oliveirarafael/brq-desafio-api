package com.brq.desafio.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brq.desafio.api.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	public List<Usuario> findByEmail(String email);
	public List<Usuario> findByCompanyId(Long id);
}
