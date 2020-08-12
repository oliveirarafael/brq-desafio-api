package com.brq.desafio.api.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class StepListener implements StepExecutionListener {
	
	private static Logger logger = LoggerFactory.getLogger(StepListener.class);
 
    @Override
    public void beforeStep(StepExecution stepExecution) {
    	logger.info("Inicio do passo " + stepExecution.getStepName() + " (" + stepExecution.getStatus().name() + ")");
    }
 
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
    	logger.info("Fim do passo " + stepExecution.getStepName() + " (" + stepExecution.getStatus().name() + ")");
    	return ExitStatus.COMPLETED;
    }
}

