package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.Calendar;
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
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
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
	private ScheduleModel sessoes;

	private Sessao sessao;
	private int anoCorrente = Constantes.ANO_CORRENTE;

	private List<Aluno> alunos;
	private List<SalaAtendimento> salas;
	private List<Paciente> pacientes;

	@PostConstruct
	public void listar() {
		sessoes = new DefaultScheduleModel();
	}

	public void novo(SelectEvent evento) {
		sessao = new Sessao();
		try {
			sessao.setData((Date) evento.getObject());
			sessao.setPaciente(new Paciente());
			alunos = new AlunoDAO().listar();
			salas = new SalaAtendimentoDAO().listar();
			pacientes = new PacienteDAO().listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao carregar os objetos da Sessão: " + erro);
		}
	}
	
	public void salvar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sessao.getData());
		calendar.add(Calendar.DATE, 1);
		sessao.setData(calendar.getTime());
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Paciente Selecionado", ((Paciente) event.getObject()).getPessoa().getNome());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ScheduleModel getSessoes() {
		return sessoes;
	}

	public void setSessoes(ScheduleModel sessoes) {
		this.sessoes = sessoes;
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

}
