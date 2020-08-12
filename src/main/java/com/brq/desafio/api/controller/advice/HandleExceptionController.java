package com.brq.desafio.api.controller.advice;

import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.brq.desafio.api.controller.advice.exception.ConflictException;
import com.brq.desafio.api.controller.advice.exception.NotFoundException;
import com.brq.desafio.api.model.Erro;
import com.brq.desafio.api.model.ErroFormulario;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class HandleExceptionController {
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = { MethodArgumentNotValidException.class, DateTimeParseException.class })
	public List<ErroFormulario> handle(MethodArgumentNotValidException exception) {
		return exception.getBindingResult().getFieldErrors().stream()
                        .map(error -> new ErroFormulario(error.getField(), error.getDefaultMessage(),
        		                            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase()))
                        .collect(Collectors.toList());

	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	public Erro handleDataInvalida(InvalidFormatException exception) {
		return new Erro("O valor '"+exception.getValue()+"' é inválido", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(JsonParseException.class)
	public Erro handleJsonParse() {
		return new Erro("Não foi possivel criar objeto com Json enviado. Certifique que a formatação do Json esta correta", 
				           HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}
	

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public Erro handleNotFound(NotFoundException exception){
		return new Erro(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(ConflictException.class)
	public Erro handleConflict(ConflictException exception){
        return new Erro(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
	}

}
