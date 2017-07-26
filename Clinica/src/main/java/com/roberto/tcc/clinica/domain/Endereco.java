package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Endereco extends GenericDomain{

//	@ManyToOne
//	@JoinColumn(nullable = false)
//	private Pessoa pessoa;
	
	@Column(length = 100, nullable = false)
	private String rua;
	
	@Column(length = 30, nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String numero;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cidade cidade;
	
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Cidade getCidade() {
		return cidade;
	}
//	public Pessoa getPessoa() {
//		return pessoa;
//	}
//	public void setPessoa(Pessoa pessoa) {
//		this.pessoa = pessoa;
//	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
}
