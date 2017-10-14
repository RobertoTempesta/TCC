package com.roberto.tcc.clinica.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
public class Sessao extends GenericDomain{

	@ManyToOne
	@JoinColumn(nullable = false)
	private SalaAtendimento sala;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Paciente paciente;
	
	@Column(length = 500)
	private String observacao;
	
	@Column(length = 1, nullable = false)
	private Character frequencia;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
	

	public SalaAtendimento getSala() {
		return sala;
	}

	public void setSala(SalaAtendimento sala) {
		this.sala = sala;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public char getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(char frequencia) {
		this.frequencia = frequencia;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setFrequencia(Character frequencia) {
		this.frequencia = frequencia;
	}
	
	
}
