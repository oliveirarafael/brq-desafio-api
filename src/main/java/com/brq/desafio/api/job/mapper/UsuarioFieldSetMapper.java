package com.brq.desafio.api.job.mapper;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.brq.desafio.api.model.entity.Usuario;
 
 
public class UsuarioFieldSetMapper implements FieldSetMapper<Usuario>{
 
    public Usuario mapFieldSet(FieldSet fieldSet) throws BindException {
    	Usuario usuario = new Usuario();
    	usuario.setCompanyId(fieldSet.readLong(0));
    	usuario.setEmail(fieldSet.readString(1));
    	Date birthDate = fieldSet.readDate(2, "dd/MM/yyyy");
    	usuario.setBirthDate(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return usuario;
    }
 
}
