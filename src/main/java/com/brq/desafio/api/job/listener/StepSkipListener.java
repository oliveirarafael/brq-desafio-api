package com.brq.desafio.api.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.brq.desafio.api.model.entity.Usuario;

@Component
public class StepSkipListener implements SkipListener<Usuario, Usuario> {
	
	private static Logger logger = LoggerFactory.getLogger(StepSkipListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		logger.error("Erro skip: ");
		logger.error(t.getMessage());
	}

	@Override
	public void onSkipInWrite(Usuario usuario, Throwable t) {
		logger.error("Registros com erro");
		logger.error(usuario.getCompanyId()+","+usuario.getEmail()+","+usuario.getBirthDate());
	    logger.error(t.getMessage());
	}

	@Override
	public void onSkipInProcess(Usuario usuario, Throwable t) {
		logger.error("Registros com erro");
		logger.error(usuario.getCompanyId()+","+usuario.getEmail()+","+usuario.getBirthDate());
	    logger.error(t.getMessage());
	}

}
