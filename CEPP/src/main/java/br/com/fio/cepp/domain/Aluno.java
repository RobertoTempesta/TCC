package br.com.fio.cepp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "aluno")
public class Aluno extends GenericDomain {

	private static final long serialVersionUID = -1268081256505091734L;

	@OneToOne
	@JoinColumn(nullable = false, name = "pessoa_codigo")
	private Pessoa pessoa;

	@NotNull(message = "RA não pode ser vazio")
	@NotBlank(message = "RA não pode ser vazio")
	@Column(nullable = false, length = 13)
	private String ra;

	@NotNull(message = "Função não pode ser vazio")
	@OneToOne
	@JoinColumn(nullable = false, name = "funcao_codigo")
	private Funcao funcao;

	@NotNull(message = "Supervisor não pode ser vazio")
	@OneToOne
	@JoinColumn(nullable = false, name = "supervisor_codigo")
	private Supervisor supervisor;

	@Column(name = "data_cadastro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
}
