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

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(length = 200, nullable = false)
	private String ocupacao;

	@Column(length = 300)
	private String tipoNecessidade;

	@Column(length = 300)
	private String usoMedicacao;

	@Column(length = 10, nullable = false)
	private String estadoCivil;

	@Column(length = 300, nullable = false)
	private String conhecimentoCentro;

	@Column(length = 150)
	private String nomePai;

	@Column(length = 150)
	private String nomeMae;

	@OneToOne
	private Pessoa responsavel;

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

	public String getTipoNecessidade() {
		return tipoNecessidade;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public void setTipoNecessidade(String tipoNecessidade) {
		this.tipoNecessidade = tipoNecessidade;
	}

	public String getUsoMedicacao() {
		return usoMedicacao;
	}

	public void setUsoMedicacao(String usoMedicacao) {
		this.usoMedicacao = usoMedicacao;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getConhecimentoCentro() {
		return conhecimentoCentro;
	}

	public void setConhecimentoCentro(String conhecimentoCentro) {
		this.conhecimentoCentro = conhecimentoCentro;
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

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}
}
