package com.brq.desafio.api.valitadion;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyValidator implements ConstraintValidator<CompanyValid, Long>{

	@Override
	public boolean isValid(Long companyId, ConstraintValidatorContext context) {	

        if(companyId == 1 || companyId == 2 || 
           companyId == 5 || companyId == 7 || companyId == 10)
          return true;
        
        return false;
	}

}
