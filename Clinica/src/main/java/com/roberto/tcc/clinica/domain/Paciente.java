package com.roberto.tcc.clinica.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.roberto.tcc.clinica.enumeracao.EstadoCivil;

@SuppressWarnings("serial")
@Entity
public class Paciente extends GenericDomain {

	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;
	
	@Column(length = 100, nullable = false)
	private String ocupacao;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;
	
	@Column(length = 80, nullable = false)
	private String nomePai;

	@Column(length = 80, nullable = false)
	private String nomeMae;

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
	
	@Column(length = 80, nullable = false)
	private String responsavelNome;
	
	@Column(length = 13)
	private String responsavelTel;
	
	@Column(length = 13)
	private String responsavelCel;

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

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getResponsavelNome() {
		return responsavelNome;
	}

	public void setResponsavelNome(String responsavelNome) {
		this.responsavelNome = responsavelNome;
	}

	public String getResponsavelTel() {
		return responsavelTel;
	}

	public void setResponsavelTel(String responsavelTel) {
		this.responsavelTel = responsavelTel;
	}

	public String getResponsavelCel() {
		return responsavelCel;
	}

	public void setResponsavelCel(String responsavelCel) {
		this.responsavelCel = responsavelCel;
	}
}
