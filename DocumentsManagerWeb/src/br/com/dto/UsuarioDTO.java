package br.com.dto;

import br.com.crypt.CriptoAES;
import br.com.entities.Usuario;

/**
 * 
 *  Funcionalidade: Classe auxiliar de usuario
 *  3 de dez de 2016
 *	@author Diogo.Lehner
 *
 */
public class UsuarioDTO {
	private String id;
	private String logon;
	private String password;
	private String nome;
	private String documento;

	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Usuario usuario) throws Exception {
		this.id = usuario.getId().toString();
		this.logon = CriptoAES.descriptografaAES(usuario.getLogon());
		this.password = CriptoAES.descriptografaAES(usuario.getPassword());
		this.nome = usuario.getPessoa().getNome();
		this.documento = usuario.getPessoa().getDocumento();
	}
	
	public String getLogon() {
		return logon;
	}
	public void setLogon(String logon) {
		this.logon = logon;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
