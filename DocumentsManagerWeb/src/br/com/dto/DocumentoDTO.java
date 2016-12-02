package br.com.dto;

public class DocumentoDTO {
	private String mensagem;
	private String usuarioID;

	public DocumentoDTO() {
		// TODO Auto-generated constructor stub
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
