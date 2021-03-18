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
@Table(name = "funcao")
public class Funcao extends GenericDomain {

	private static final long serialVersionUID = -6032141595294527779L;
	
	@Column(length = 50, nullable = false)
	@NotNull(message = "Descrição é obrigatório")
	@NotBlank
	private String descricao;

}
