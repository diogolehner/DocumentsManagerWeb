package br.com.controller.exceptions;

public class NonexistentEntityException extends Exception {
	private static final long serialVersionUID = -8042621639613398679L;
	public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
