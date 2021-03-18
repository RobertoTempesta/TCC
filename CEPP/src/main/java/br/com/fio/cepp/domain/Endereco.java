package br.com.fio.cepp.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {

	@NotNull(message = "Rua não pode ser vazio")
	@NotBlank(message = "Rua não pode ser vazio")
	@Column(name = "endereco_rua", length = 50, nullable = false)
	private String rua;
	
	@NotNull(message = "Numero não pode ser vazio")
	@NotBlank(message = "Numero não pode ser vazio")
	@Column(name = "endereco_numero", length = 10, nullable = false)
	private String numero;
	
	@NotNull(message = "CEP não pode ser vazio")
	@NotBlank(message = "CEP não pode ser vazio")
	@Column(name = "endereco_cep", length = 10, nullable = false)
	private String cep;
	
	@NotNull(message = "Bairro não pode ser vazio")
	@NotBlank(message = "Bairro não pode ser vazio")
	@Column(name = "endereco_bairro", length = 50, nullable = false)
	private String bairro;
	
	@NotNull(message = "Cidade não pode ser vazio")
	@NotBlank(message = "Cidade não pode ser vazio")
	@Column(name = "endereco_cidade", length = 50, nullable = false)
	private String cidade;

	@NotNull(message = "Estado não pode ser vazio")
	@ManyToOne
	@JoinColumn(name = "endereco_estado_codigo",nullable = false)
	private Estado estado;
}
