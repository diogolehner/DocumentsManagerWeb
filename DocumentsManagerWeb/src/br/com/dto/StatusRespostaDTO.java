package br.com.dto;

/**
 * 
 *  Funcionalidade: Classe auxiliar com status da resposta do backend para o front
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class StatusRespostaDTO {

	private String status;
	private String mensagem;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
