package br.com.controller.exceptions;

public class PessoaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String error;
	
	public PessoaException(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}
	
	
}
