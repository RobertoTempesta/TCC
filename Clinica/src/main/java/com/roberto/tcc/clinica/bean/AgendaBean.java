package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.SalaAtendimento;
import com.roberto.tcc.clinica.domain.Sessao;
import com.roberto.tcc.clinica.util.Constantes;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAgenda")
@ViewScoped
public class AgendaBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(AgendaBean.class);
	private ScheduleModel lista;

	private Sessao sessao;
	private int anoCorrente = Constantes.ANO_CORRENTE;

	private List<Aluno> alunos;
	private List<SalaAtendimento> salas;
	private List<Paciente> pacientes;
	private List<Sessao> sessoes;

	@PostConstruct
	public void init() {
		lista = new DefaultScheduleModel();

		try {

			sessoes = new SessaoDAO().listar();

			for (Sessao s : sessoes) {

				DefaultScheduleEvent evento = new DefaultScheduleEvent();
				evento.setEndDate(s.getDataFim());
				evento.setStartDate(s.getDataInicio());
				evento.setData(s.getCodigo());
				evento.setTitle(s.getPaciente().getPessoa().getNome());
				evento.setDescription(s.getPaciente().getNumeroCaso());
				evento.setAllDay(false);
				evento.setEditable(false);

				lista.addEvent(evento);

			}

		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar carregar as sessões: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao carregar as Sessões");
		}

	}

	public void novo(SelectEvent evento) {

		try {
			sessao = new Sessao();
			sessao.setAluno(new Aluno());
			sessao.setPaciente(new Paciente());
			sessao.setSala(new SalaAtendimento());

			sessao.setDataInicio((Date) evento.getObject());
			sessao.setDataFim((Date) evento.getObject());

			alunos = new AlunoDAO().listar();
			salas = new SalaAtendimentoDAO().listar();
			pacientes = new PacienteDAO().listar();

		} catch (RuntimeException erro) {
			logger.error("Erro ao carregar as datas da Sessão/Alunos/Salas/Pacientes: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao carregar a data selecionada");
		}
	}

	public void salvar() {

		// sessao.getPaciente().setNumeroCaso((sessao.getPaciente().getNumeroCaso() +
		// anoCorrente));

		SessaoDAO dao = new SessaoDAO();
		dao.salvarPrimeiraSessao(sessao);
		sessoes = new SessaoDAO().listar();
		RequestContext.getCurrentInstance().execute("PF('dlgSessao').hide();");
	}

	public void onRowSelect(SelectEvent event) {
		Messages.addGlobalInfo("Paciente Selecionado");
	}

	public void eventoSelecionado(SelectEvent selectEvent) {
		ScheduleEvent evento = (ScheduleEvent) selectEvent.getObject();

		sessao = new Sessao();
		sessao.setAluno(new Aluno());
		sessao.setPaciente(new Paciente());
		sessao.setSala(new SalaAtendimento());

		alunos = new AlunoDAO().listar();
		salas = new SalaAtendimentoDAO().listar();
		pacientes = new PacienteDAO().listar();

		for (Sessao sessao : sessoes) {
			if (sessao.getCodigo() == (Long) evento.getData()) {
				this.sessao = sessao;
				break;
			}
		}
	}

	public ScheduleModel getLista() {
		return lista;
	}

	public void setLista(ScheduleModel lista) {
		this.lista = lista;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<SalaAtendimento> getSalas() {
		return salas;
	}

	public void setSalas(List<SalaAtendimento> salas) {
		this.salas = salas;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

	public int getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(int anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}

}
