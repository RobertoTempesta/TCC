package com.roberto.tcc.clinica.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.roberto.tcc.clinica.enumeracao.Sexo;

@SuppressWarnings("serial")
@Entity
public class Pessoa extends GenericDomain{

	@Column(length = 50, nullable = false)
	private String nome;

	@Column(length = 14, unique = true, nullable = false)
	private String CPF;
	
	@Column(length = 12, nullable = false)
	private String RG;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(length = 3, nullable = false)
	private Integer idade;
	
	@Column(length = 100, nullable = false)
	private String escolaridade;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false)
	private Endereco endereco;
	
	@Column(length = 13, nullable = false)
	private String telefone1;
	
	@Column(length = 13)
	private String telefone2;
	
	@Column(length = 13)
	private String telefone3;
	
	@Column(length = 13)
	private String telefone4;
	
	@Column(length = 100)
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public String getTelefone4() {
		return telefone4;
	}

	public void setTelefone4(String telefone4) {
		this.telefone4 = telefone4;
	}

	public Integer getIdade() {
		return idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}


}
