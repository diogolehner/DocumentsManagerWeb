package br.com.types;

public enum TipoAuditoria {
	LOGON,
	LOGOUT,
	ACESSO_MANAGER, 
	CADASTRO_MANAGER,
	ACESSO_DOCUMENTO,
	CADASTRO_CODUMENTO,
	ACESSO_MENSAGEM,
	NOVO_USUARIO; 
	
	public String getCodigo() {
		return this.toString();
	}
}
