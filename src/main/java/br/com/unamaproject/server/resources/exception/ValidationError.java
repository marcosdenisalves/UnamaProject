package br.com.unamaproject.server.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}

	private List<FieldMessage> errors = new ArrayList<>();
	

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
