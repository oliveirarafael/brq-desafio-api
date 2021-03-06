package com.brq.desafio.api.valitadion;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<BirthDateValid, LocalDate>{

	@Override
	public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {	
        LocalDate hoje = LocalDate.now();
        
        if(birthDate.isAfter(hoje))
		   return false;
        
        return true;
	}

}
