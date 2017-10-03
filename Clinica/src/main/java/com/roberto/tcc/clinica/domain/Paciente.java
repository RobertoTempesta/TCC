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
public class Paciente extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(length = 100, nullable = false)
	private String ocupacao;
	
	@Column(length = 1, nullable = false)
	private char estadoCivil;
	
	@Column(length = 80, nullable = false)
	private String nomePai;

	@Column(length = 80, nullable = false)
	private String nomeMae;

	@OneToOne
	private Responsavel responsavel;

	@Column(length = 100)
	private String medicamento;
	
	@Column(length = 500)
	private String observacao;
	
	@Column(length = 100)
	private String necessidadesEspeciais;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(length = 50)
	private String numeroCaso;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getOcupacao() {
		return ocupacao;
	}

	public void setOcupacao(String ocupacao) {
		this.ocupacao = ocupacao;
	}

	public char getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(char estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public String getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNecessidadesEspeciais() {
		return necessidadesEspeciais;
	}

	public void setNecessidadesEspeciais(String necessidadesEspeciais) {
		this.necessidadesEspeciais = necessidadesEspeciais;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNumeroCaso() {
		return numeroCaso;
	}

	public void setNumeroCaso(String numeroCaso) {
		this.numeroCaso = numeroCaso;
	}
}
