package br.com.dto;

/**
 * 
 *  Funcionalidade: Classe auxiliar com permissao do usuario
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class UsuarioPermissaoDTO {
	private String logon;
	private String permissao;

	public UsuarioPermissaoDTO() {
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
