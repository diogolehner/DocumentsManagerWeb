package br.com.controller.exceptions;

public class PreexistingEntityException extends Exception {
	private static final long serialVersionUID = -6020927087586809739L;
	public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
