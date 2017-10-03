package com.roberto.tcc.clinica.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Endereco extends GenericDomain{
	
	@Column(length = 50, nullable = false)
	private String rua;
	
	@Column(length = 10, nullable = false)
	private String numero;
	
	@Column(length = 10, nullable = false)
	private String CEP;
	
	@Column(length = 50, nullable = false)
	private String bairro;
	
	@Column(length = 50, nullable = false)
	private String cidade;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Estado estado;

	public String getRua() {
		return rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cEP) {
		CEP = cEP;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
