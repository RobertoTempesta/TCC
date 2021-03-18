package br.com.fio.cepp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import br.com.fio.cepp.domain.enumeracao.EstadoCivil;
import br.com.fio.cepp.domain.enumeracao.Situacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paciente")
public class Paciente extends GenericDomain {

	private static final long serialVersionUID = -1199394053898928424L;

	@OneToOne
	@JoinColumn(nullable = false, name = "pessoa_codigo")
	private Pessoa pessoa;

	@NotNull(message = "Ocupação é obrigatório")
	@NotBlank(message = "Ocupação não pode ser vazio")
	@Column(length = 100, nullable = false)
	private String ocupacao;

	@NotNull(message = "Estado Civil é obrigatório")
	@Column(nullable = false, name = "estado_civil")
	@Enumerated(EnumType.STRING)
	private EstadoCivil estadoCivil;

	@Column(nullable = false, length = 2)
	@Enumerated(EnumType.STRING)
	private Situacao situacao;

	@NotNull(message = "Nome do Pai é obrigatório")
	@NotBlank(message = "Nome do Pai não pode ser vazio")
	@Column(length = 80, nullable = false, name = "nome_pai")
	private String nomePai;

	@NotNull(message = "Nome da Mãe é obrigatório")
	@NotBlank(message = "Nome da Mãe não pode ser vazio")
	@Column(length = 80, nullable = false, name = "nome_mae")
	private String nomeMae;

	@Column(length = 100)
	private String medicamento;

	@Column(length = 500)
	private String observacao;
	
	@Column(nullable = true, name = "numero_caso")
	private String numeroCaso;

	@Column(length = 100, name = "necessidades_especiais")
	private String necessidadesEspeciais;

	@Column(nullable = false, name = "data_cadastro")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@NotNull(message = "Nome do Responsável é obrigatório")
	@NotBlank(message = "Nome do Responsável não pode ser vazio")
	@Column(length = 80, nullable = false, name = "responsavel_nome")
	private String responsavelNome;

	@Column(length = 13, name = "responsavel_telefone")
	private String responsavelTelefone;

	@Column(length = 13, name = "responsavel_celular")
	private String responsavelCel;

	@NotNull(message = "Pessoa Autorizada é obrigatório")
	@NotBlank(message = "Pessoa Autorizada não pode ser vazio")
	@Column(length = 80, nullable = true, name = "pessoa_autorizada")
	private String pessoaAutorizada;
}
