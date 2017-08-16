package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Cargo extends GenericDomain{

	@Column(length = 20, nullable = false)
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
