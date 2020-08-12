package com.brq.desafio.api.job;

import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.brq.desafio.api.controller.advice.exception.ConflictException;
import com.brq.desafio.api.job.listener.JobListener;
import com.brq.desafio.api.job.listener.StepItemReadListener;
import com.brq.desafio.api.job.listener.StepSkipListener;
import com.brq.desafio.api.job.listener.StepListener;
import com.brq.desafio.api.job.listener.StepItemWriteListener;
import com.brq.desafio.api.job.mapper.UsuarioFieldSetMapper;
import com.brq.desafio.api.job.tasklet.BackupArquivosTasklet;
import com.brq.desafio.api.job.writer.UsuarioWriter;
import com.brq.desafio.api.model.entity.Usuario;

@Configuration
@EnableBatchProcessing
public class UsuarioJob {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
 
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private JobListener jobListener;
     
    @Autowired
    private StepListener stepListener;
    
    @Autowired 
    private StepItemWriteListener writeListener;
    
    @Autowired
    private StepItemReadListener readListener;
    
    @Autowired
    private StepSkipListener skipListener;
    
    @Value("${batch.path.diretory}")
    private Resource diretorioArquivos;
    
    @Value("${batch.path.diretory.backup}")
    private Resource diretorioBackupArquivos;
    
    @Value("${batch.path.files}")
    private Resource[] arquivos;
    
    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("usuariosCSV")
                .listener(this.jobListener)        
                .incrementer(new RunIdIncrementer())
                .start(inserirDadosArquivos())
                .next(backupArquivos())
                .build();
    }
    
    @Bean
    public Step inserirDadosArquivos() {
        return stepBuilderFactory.get("step")
        		                 .listener(stepListener)
        		                 .<Usuario, Usuario>chunk(10)
                                 .reader(multiResourceItemReader())                             
                                 .listener(readListener)
                                 .writer(writer())
        		                 .listener(writeListener)
                                 .faultTolerant()
                                 .skipLimit(100)
                                 .skip(RuntimeException.class)
                                 .noSkip(FileNotFoundException.class)
                                 .listener(skipListener)
                                 .build();
    }
    
    @Bean
    public Step backupArquivos() {
    	return this.stepBuilderFactory.get("backupArquivos")
    				.tasklet(backupArquivosTasklet())
    				.listener(stepListener)
    				.build();
    }
     
    @Bean
    public MultiResourceItemReader<Usuario> multiResourceItemReader() 
    {
    	MultiResourceItemReader<Usuario> resourceItemReader = new MultiResourceItemReader<Usuario>();
    	if(arquivos.length > 0) {
           resourceItemReader.setResources(arquivos);
           resourceItemReader.setDelegate(reader());
    	}
        return resourceItemReader;
    }
     
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<Usuario> reader() 
    {
        FlatFileItemReader<Usuario> reader = new FlatFileItemReader<Usuario>();
        reader.setLinesToSkip(1);   
        reader.setLineMapper(new DefaultLineMapper() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(";"));
                setFieldSetMapper(new UsuarioFieldSetMapper());
            }
        });
        return reader;
    }
    
    @Bean
    public UsuarioWriter<Usuario> writer() 
    {
        return new UsuarioWriter<Usuario>();
    }
    
    @Bean
    public BackupArquivosTasklet backupArquivosTasklet() {
    	BackupArquivosTasklet tasklet = new BackupArquivosTasklet();
    	tasklet.setDirectorioArquivos(diretorioArquivos);
    	tasklet.setDirectorioBackupArquivos(diretorioBackupArquivos);
    	return tasklet;
    }
  

}
