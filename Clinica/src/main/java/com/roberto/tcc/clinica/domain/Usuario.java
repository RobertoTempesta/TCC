package com.roberto.tcc.clinica.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain{

	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private Pessoa pessoa;
	
	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private String salt;
	
	@Column(nullable = false)
	private Character tipo;
	
	@Column(nullable = false)
	private Boolean ativo;

	public String getSalt() {
		return salt;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSenha() {
		return senha;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
