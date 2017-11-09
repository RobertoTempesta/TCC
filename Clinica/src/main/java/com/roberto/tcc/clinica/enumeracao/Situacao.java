package com.roberto.tcc.clinica.enumeracao;

public enum Situacao {
	
	AGUARDANDO ("Aguardando", "font-weight: bold; color: green; text-align: center;"), 
	EM_ANDAMENTO("Sendo Atendido", "font-weight: bold; color: #fea12c; text-align: center;"),
	FINALIZADO("Finalizado", "font-weight: bold; color: red; text-align: center;");
	
	private String descricao;
	private String css;
	
	private Situacao(String descricao, String css){
		this.descricao = descricao;
		this.css = css;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
}
