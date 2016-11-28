package br.com.dto;

import br.com.crypt.CryptoUtils;
import br.com.entities.Usuario;

public class UsuarioDTO {
	private String id;
	private String logon;
	private String password;
	private String nome;
	private String documento;

	public UsuarioDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public UsuarioDTO(Usuario usuario) throws Exception {
		this.id = usuario.getId().toString();
		this.logon = CryptoUtils.descriptografaAES(usuario.getLogon());
		this.password = CryptoUtils.descriptografaAES(usuario.getPassword());
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
