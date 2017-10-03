package com.roberto.tcc.clinica.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Aluno extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	@Column(nullable = false, length = 13)
	private String RA;

	@OneToOne
	@JoinColumn(nullable = false)
	private Funcao funcao;

	@OneToOne
	@JoinColumn(nullable = false)
	private Supervisor supervisor;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
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

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
}
