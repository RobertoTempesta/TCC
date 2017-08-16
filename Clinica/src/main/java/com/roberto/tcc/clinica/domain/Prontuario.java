package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Prontuario extends GenericDomain{

	@Column(length = 50, nullable = false)
	private String numeroCaso;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Paciente paciente;

	public String getNumeroCaso() {
		return numeroCaso;
	}

	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	
}
