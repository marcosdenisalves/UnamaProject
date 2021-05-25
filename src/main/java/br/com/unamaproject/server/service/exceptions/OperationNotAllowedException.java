package br.com.unamaproject.server.service.exceptions;

public class OperationNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public OperationNotAllowedException(String msg) {
		super(msg);
	}
	
	public OperationNotAllowedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
