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
@Table(name = "sala_atendimento")
public class SalaAtendimento extends GenericDomain {

	private static final long serialVersionUID = 1449831401765063870L;

	@Column(length = 25, nullable = false)
	@NotNull(message = "Descrição/Número não pode ser Null")
	@NotBlank(message = "Descrição/Número não pode estar em Banco")
	private String descricao;
}
