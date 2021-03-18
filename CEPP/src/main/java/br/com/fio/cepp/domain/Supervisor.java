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
@Table(name = "supervisor")
public class Supervisor extends GenericDomain {

	private static final long serialVersionUID = 5531551071872086971L;

	@OneToOne
	@JoinColumn(nullable = false, name = "pessoa_codigo")
	private Pessoa pessoa;
	
	@NotNull(message = "CRP não pode estar em branco")
	@NotBlank(message = "CRP não pode ser nulo")
	@Column(length = 15, nullable = false)
	private String crp;
	
	@NotNull(message = "Data de cadastro não pode ser nula")
	@Column(name = "data_cadastro", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
}
