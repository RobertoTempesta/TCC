package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")
@Entity
public class Telefone extends GenericDomain{
	
	@Column(length = 20)
	private String numero;
	
	@Column(length = 300)
	private String observacoes;
	
	@ManyToOne
	private Pessoa pessoa;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getObservacoes() {
		return observacoes;
	}

	@Override
	public String toString() {
		return "Telefone [numero=" + numero + "]";
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}
