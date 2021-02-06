package com.devsuperior.dscatalog.services.exceptions;

//Pode extender tanto de RuntimeException quanto de Excpetion
//Se extender de Exception será obrigado a tratar a exceção
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
}
