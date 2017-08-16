package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Supervisor extends GenericDomain{

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(length = 15, nullable = false)
	private String CRP;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getCRP() {
		return CRP;
	}

	public void setCRP(String cRP) {
		CRP = cRP;
	}
	
}
