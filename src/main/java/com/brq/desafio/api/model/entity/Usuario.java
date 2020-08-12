package com.brq.desafio.api.model.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.brq.desafio.api.valitadion.BirthDateValid;
import com.brq.desafio.api.valitadion.CompanyValid;
import com.brq.desafio.api.view.UsuarioView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(value = {UsuarioView.DTO.class})
    private Long userId;
    
    @NotEmpty
    @Email(message = "{email.not.valid}")
    @JsonView(value = {UsuarioView.DTO.class, UsuarioView.FORM.Post.class, UsuarioView.FORM.Put.class})
    private String email;
    
    @NotNull
    @BirthDateValid
    @JsonView(value = {UsuarioView.DTO.class, UsuarioView.FORM.Post.class, UsuarioView.FORM.Put.class})
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    
    @NotNull
    @JsonView(value = {UsuarioView.DTO.class, UsuarioView.FORM.Post.class, UsuarioView.FORM.Put.class})
    @CompanyValid
    private Long companyId;
        
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public Long getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Usuario [userId=" + userId + ", email=" + email + ", birthDate=" + birthDate + ", companyId="
				+ companyId + "]";
	}

}