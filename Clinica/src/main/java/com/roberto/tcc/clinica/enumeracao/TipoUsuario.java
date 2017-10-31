package com.roberto.tcc.clinica.enumeracao;

public enum TipoUsuario {

	GERENCIADOR("Gerênciador"), USUARIO("Usuário");

	private String descricao;

	private TipoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
