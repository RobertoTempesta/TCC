package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class SalaAtendimento extends GenericDomain{

	@Column(length = 10, nullable = false)
	private String numeroSala;

	public String getNumeroSala() {
		return numeroSala;
	}

	public void setNumeroSala(String numeroSala) {
		this.numeroSala = numeroSala;
	}
	
}
