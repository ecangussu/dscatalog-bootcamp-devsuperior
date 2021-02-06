package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

//Manipulador de exceções a nível da camada de Resource
//Esta anotação permite que está classe intercepte exceções que ocorrerem na camada de controladores rest (resources) e passará a tratar esta exceção
@ControllerAdvice
public class ResourceExceptionHandler {
	
	//HttpServletRequest = Objeto que terá as informações da requisição
	//@ExceptionHandler = informa que se trata de um método que irá tratar a exceção interceptada 
	//EntityNotFoundException.class = para saber o tipo de exceção que deve se interceptar para realizar o tratamento
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		StandardError error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setError("Resource not found");
		error.setMessage(e.getMessage());
		//Pega o caminho da requisição feita
		error.setPath(request.getRequestURI());
		//.status = permite customizar o status que será retornado
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

}
