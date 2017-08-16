package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Telefone extends GenericDomain{
	
	@Column(length = 10)
	private String numero;
	
	@Column(length = 300)
	private String observacoes;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}
