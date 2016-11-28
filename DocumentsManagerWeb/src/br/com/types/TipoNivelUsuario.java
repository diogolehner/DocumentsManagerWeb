package br.com.types;

public enum TipoNivelUsuario {
	ADMIN_1, //Acesso total
	USER_1, //Usuario, permissao para ler e gravar arquivos
	REQUESTER_1; //Solicitante, admin necessita liberar cadastro
	
	public String getCodigo() {
		return this.toString();
	}
}
