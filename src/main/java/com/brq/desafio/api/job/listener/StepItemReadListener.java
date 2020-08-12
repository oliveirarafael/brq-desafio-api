package com.brq.desafio.api.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.brq.desafio.api.model.entity.Usuario;

@Component
public class StepItemReadListener implements ItemReadListener<Usuario>{

	private static Logger logger = LoggerFactory.getLogger(StepItemReadListener.class);
	
	@Override
	public void beforeRead() {
		logger.info("Iniciou a leitura dos dados");
	}

	@Override
	public void afterRead(Usuario usuario) {
		logger.info("Registro lido");
		logger.info(usuario.getCompanyId()+","+usuario.getEmail()+","+usuario.getBirthDate());
	}

	@Override
	public void onReadError(Exception exception) {
		logger.error("Erro ao ler registro");
	    logger.error(exception.getMessage());
	}

}
