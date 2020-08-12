package com.brq.desafio.api.job.tasklet;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.google.common.io.Files;

public class BackupArquivosTasklet implements Tasklet, InitializingBean {

	private Resource diretorioArquivo;
	private Resource diretorioBackupArquivo;

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		File dir = diretorioArquivo.getFile();

		if (dir.isDirectory()) {
			
			File backup = diretorioBackupArquivo.getFile();
			
			if(!backup.exists()) {
				backup.mkdir();
			}

			File[] files = dir.listFiles();
			for (File file : files) {	
				if(file.isFile())
				   Files.move(file, new File(backup.getAbsolutePath()+File.separator+file.getName()));
			}
		}
		return RepeatStatus.FINISHED;
	}

	public void setDirectorioArquivos(Resource diretorioArquivo) {
		this.diretorioArquivo = diretorioArquivo;
	}
	
	public void setDirectorioBackupArquivos(Resource diretorioBackupArquivo) {
		this.diretorioBackupArquivo = diretorioBackupArquivo;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(diretorioArquivo, "O diretorio dos arquivos precisa ser definido");
		Assert.notNull(diretorioBackupArquivo, "O diretorio de backup precisa ser definido");
	}
}
