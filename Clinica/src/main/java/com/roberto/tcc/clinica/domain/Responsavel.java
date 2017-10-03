package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Responsavel extends GenericDomain{
	
	@Column(length = 50, nullable = false)
	private String nome;
	
	@Column(length = 13, nullable = false)
	private String telefone;
	
	@Column(length = 13, nullable = false)
	private String celular;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	

}
