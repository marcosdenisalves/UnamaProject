package br.com.unamaproject.server.service.exceptions;

public class InvalidFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public InvalidFormatException(String msg) {
		super(msg);
	}
	
	public InvalidFormatException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
