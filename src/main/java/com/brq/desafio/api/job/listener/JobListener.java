package com.brq.desafio.api.job.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobListener implements JobExecutionListener{
	
	private static Logger logger = LoggerFactory.getLogger(JobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info("Iniciou o Job " + jobExecution.getId() + " (" + jobExecution.getStatus().name() + ")");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info("Iniciou o Job " + jobExecution.getId() + " (" + jobExecution.getStatus().name() + ")");
	}

}
