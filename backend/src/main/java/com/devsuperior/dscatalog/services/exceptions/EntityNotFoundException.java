package com.devsuperior.dscatalog.services.exceptions;

//Pode extender tanto de RuntimeException quanto de Excpetion
//Se extender de Exception será obrigado a tratar a exceção
public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String msg) {
		super(msg);
	}
	
}
