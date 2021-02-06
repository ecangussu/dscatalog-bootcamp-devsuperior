package com.devsuperior.dscatalog.services.exceptions;

//Pode extender tanto de RuntimeException quanto de Excpetion
//Se extender de Exception será obrigado a tratar a exceção
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseException(String msg) {
		super(msg);
	}
	
}
