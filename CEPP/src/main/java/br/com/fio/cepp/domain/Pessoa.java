package br.com.fio.cepp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.fio.cepp.domain.enumeracao.Sexo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa extends GenericDomain {

	private static final long serialVersionUID = -6387423167655393233L;

	@NotNull(message = "Nome não pode ser vazio")
	@NotBlank(message = "Nome não pode ser vazio")
	@Column(length = 50, nullable = false)
	private String nome;

	@NotNull(message = "CPF não pode ser vazio")
	@NotBlank(message = "CPF não pode ser vazio")
	@Column(length = 14, unique = true, nullable = false)
	private String cpf;
	
	@NotNull(message = "RG não pode ser vazio")
	@NotBlank(message = "RG não pode ser vazio")
	@Column(length = 12, nullable = false)
	private String rg;

	@NotNull(message = "Gênero não pode ser vazio")
	@Column(nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	
	@NotNull(message = "Data de nascimento não pode ser vazio")
	@Column(name = "data_nascimento", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@NotNull(message = "Idade não pode ser vazio")
	@Column(length = 3, nullable = false)
	private Integer idade;
	
	@NotNull(message = "Formação Acadêmica não pode ser vazio")
	@ManyToOne
	@JoinColumn(nullable = false, name = "formacao_academica_codigo")
	private FormacaoAcademica formacaoAcademica;
	
	@Embedded
	private Endereco endereco;
	
	@Column(length = 100)
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pessoa")
	private List<Telefone> telefones = new ArrayList<>();
}
