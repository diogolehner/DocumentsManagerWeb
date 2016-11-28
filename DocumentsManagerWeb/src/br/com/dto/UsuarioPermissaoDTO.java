package br.com.dto;

public class UsuarioPermissaoDTO {
	private String logon;
	private String permissao;

	public UsuarioPermissaoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioPermissaoDTO(String logon, String permissao) throws Exception {
		this.logon = logon;
		this.permissao = permissao;
	}

	public String getLogon() {
		return logon;
	}

	public void setLogon(String logon) {
		this.logon = logon;
	}

	public String getPermissao() {
		return permissao;
	}

	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	
}
