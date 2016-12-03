package br.com.dto;

/**
 * 
 *  Funcionalidade: Classe auxiliar que mantem um token
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class TokenDTO {

	private String token = "vazio";
	private String idUsuario;
	private String nomeUsuario;
	private String permissao;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
	public String getPermissao() {
		return permissao;
	}
	public void setPermissao(String permissao) {
		this.permissao = permissao;
	}
	
}
