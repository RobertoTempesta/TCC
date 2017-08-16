package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Aluno extends GenericDomain{

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(nullable = false, length = 13)
	private String RA;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Cargo cargo;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getRA() {
		return RA;
	}

	public void setRA(String rA) {
		RA = rA;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	
}
