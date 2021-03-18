package br.com.fio.cepp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "formacao_academica")
public class FormacaoAcademica extends GenericDomain {

	private static final long serialVersionUID = -4466418774509008342L;
	
	@Column(length = 150, nullable = false)
	@NotNull(message = "Descrição é obrigatório")
	@NotBlank
	private String descricao;
}
