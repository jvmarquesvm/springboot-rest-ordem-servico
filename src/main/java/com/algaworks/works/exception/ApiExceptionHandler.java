package com.algaworks.works.exception;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.works.exception.Problema.Campo;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Problema problem = new Problema();
		problem.setStatus(status.value());
		problem.setTitulo(ex.getMessage());
		problem.setDataHora(OffsetDateTime.now());
		
		return super.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Problema problem = new Problema();
		
		problem.setStatus(status.value());
		problem.setTitulo("Um ou mais campos com problema");
		problem.setDataHora(OffsetDateTime.now());
		
		List<Problema.Campo> campos = new ArrayList<Problema.Campo>();
		
		for(ObjectError error :  ex.getBindingResult().getAllErrors()) {
			//String mensagem = error.getDefaultMessage();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			String nome = ((FieldError) error).getField();
			campos.add(new Problema.Campo(nome, mensagem));
		}
		
		problem.setCampos( campos );
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
}
