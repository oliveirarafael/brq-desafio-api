package com.brq.desafio.api.job.listener;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.brq.desafio.api.model.entity.Usuario;

@Component
public class StepItemWriteListener implements ItemWriteListener<Usuario>{
	
	private static Logger logger = LoggerFactory.getLogger(StepItemWriteListener.class);

	@Override
	public void beforeWrite(List<? extends Usuario> usuarios) {
		logger.info("Registros antes de incluir na base");
		usuarios.forEach(u -> logger.info(u.getCompanyId()+","+u.getEmail()+","+u.getBirthDate()));
	}

	@Override
	public void afterWrite(List<? extends Usuario> usuarios) {
		logger.info("Registros incluido na base");
		usuarios.forEach(u -> logger.info(u.getCompanyId()+","+u.getEmail()+","+u.getBirthDate()));
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Usuario> usuarios) {
		logger.error("Registros com erro");
		usuarios.forEach(u -> {
			    logger.error(u.getCompanyId()+","+u.getEmail()+","+u.getBirthDate());
			    logger.error(exception.getMessage());
			});
		
	}

}
