package br.com.dto;

/**
 * 
 *  Funcionalidade: Classe auxiliar de documentos
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class DocumentoDTO {
	private String mensagem;
	private String usuarioID;

	public DocumentoDTO() {
	}
	
	public DocumentoDTO(String mensagem, String usuarioID) throws Exception {
		this.mensagem = mensagem;
		this.usuarioID = usuarioID;
	}

	public String getUsuarioID() {
		return usuarioID;
	}

	public void setUsuarioID(String usuarioID) {
		this.usuarioID = usuarioID;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
