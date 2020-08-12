package com.brq.desafio.api.job.writer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brq.desafio.api.model.entity.Usuario;
import com.brq.desafio.api.service.UsuarioService;

@Component
public class UsuarioWriter<Usuario> implements ItemWriter<Usuario> { 
	
	@Autowired
	private UsuarioService usuarioService;
	
    @Override
    @Transactional
    public void write(List<? extends Usuario> usuarios) throws Exception { 
    	usuarios.forEach( usuario -> usuarioService.salvar((com.brq.desafio.api.model.entity.Usuario) usuario));
    } 
}
