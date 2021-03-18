package br.com.fio.cepp.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import br.com.fio.cepp.domain.enumeracao.PresencaStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sessao")
public class Sessao extends GenericDomain {

	private static final long serialVersionUID = -2218908359805783714L;

	@NotNull(message = "Sala de Atendimento é obrigatório")
	@ManyToOne
	@JoinColumn(nullable = false, name = "sala_atendimento_codigo")
	private SalaAtendimento salaAtendimento;
	
	@NotNull(message = "Aluno é obrigatório")
	@ManyToOne
	@JoinColumn(nullable = false, name = "aluno_codigo")
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(nullable = false, name = "paciente_codigo")
	private Paciente paciente;
	
	@Column(length = 500)
	private String observacao;
	
	@Column(nullable = false, length = 2, name = "presenca_status")
	@Enumerated(EnumType.STRING)
	private PresencaStatus presencaStatus;
	
	@NotNull(message = "Início é obrigatório")
	@Column(nullable = false , name = "data_inicio")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	
	@NotNull(message = "Fim é obrigatório")
	@Column(nullable = false, name = "data_fim")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
}
