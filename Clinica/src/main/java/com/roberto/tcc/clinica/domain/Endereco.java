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
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade cidade;


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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	
}
