package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
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
	private ScheduleModel sessoesMarcadas;

	private Sessao sessao;
	private int anoCorrente = Constantes.ANO_CORRENTE;

	private List<Aluno> alunos;
	private List<SalaAtendimento> salas;
	private List<Paciente> pacientes;
	private List<Sessao> sessoes;

	private Date hora;

	@PostConstruct
	public void listar() {
		sessoesMarcadas = new DefaultScheduleModel();
		listarSessoes();
		for (Sessao s : sessoes) {

			 DefaultScheduleEvent evento = new DefaultScheduleEvent();
			 evento.setEndDate(s.getDataFim());
			 evento.setStartDate(s.getDataInicio());
			 evento.setData(s.getDataInicio());
			 evento.setTitle(s.getPaciente().getPessoa().getNome());
			 evento.setDescription(s.getPaciente().getNumeroCaso());
			 evento.setAllDay(false);
			 evento.setEditable(false);


//			sessoesMarcadas.addEvent(
//					new DefaultScheduleEvent(s.getPaciente().getPessoa().getNome(), s.getData(), s.getData()));
			 sessoesMarcadas.addEvent(evento);

		}

	}

	public void listarSessoes() {
		try {
			sessoes = new SessaoDAO().listar();
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar carregar as sessões: " + erro);
		}
	}

	public void novo(SelectEvent evento) {
		sessao = new Sessao();
		try {
			sessao.setDataInicio((Date) evento.getObject());
			sessao.setDataFim((Date) evento.getObject());
			sessao.setPaciente(new Paciente());
			alunos = new AlunoDAO().listar();
			salas = new SalaAtendimentoDAO().listar();
			pacientes = new PacienteDAO().listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao carregar os objetos da Sessão: " + erro);
		}
	}

	public void salvar() {

		sessao.getPaciente().setNumeroCaso((sessao.getPaciente().getNumeroCaso() + anoCorrente));
		sessao.setFrequencia('N');

		SessaoDAO dao = new SessaoDAO();
		dao.salvarPrimeiraSessao(sessao);

		listar();
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Paciente Selecionado",
				((Paciente) event.getObject()).getPessoa().getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ScheduleModel getSessoesMarcadas() {
		return sessoesMarcadas;
	}

	public void setSessoesMarcadas(ScheduleModel sessoes) {
		this.sessoesMarcadas = sessoes;
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

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

}
