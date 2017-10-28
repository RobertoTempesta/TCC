package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.SalaAtendimento;
import com.roberto.tcc.clinica.domain.Sessao;
import com.roberto.tcc.clinica.util.Constantes;

@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 3395759380539498382L;

	private Paciente paciente = null;
	private Sessao sessao = null;
	private int anoCorrente = Constantes.ANO_CORRENTE;

	private List<Estado> estados = null;
	private List<Paciente> pacientes = null;
	private List<Aluno> alunos = null;
	private List<SalaAtendimento> salas = null;

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		sessao = new Sessao();
		sessao.setAluno(new Aluno());
		sessao.setSala(new SalaAtendimento());
		sessao.setPaciente(new Paciente());
		carregarPacientes();
	}

	public void carregarPacientes() {
		try {
			pacientes = new PacienteDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao listar os Pacientes", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar os pacientes.");
		}
	}

	public void carregarObjetosSessao() {
		try {
			alunos = new AlunoDAO().listar();
			salas = new SalaAtendimentoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao carregar os alunos e as salas: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao carregar os objetos da Tela!");
		}
	}
	
	public void capturaPaciente(ActionEvent evento) {
		this.paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
		carregarObjetosSessao();
	}

	public void salvar() {
		try {
			SessaoDAO sessaoDAO = new SessaoDAO();
			sessao.setPaciente(paciente);
			sessaoDAO.salvarPrimeiraSessao(sessao);
			Messages.addGlobalInfo("Sessão salva com Sucesso!");
			RequestContext.getCurrentInstance().execute("PF('dlgSessao').hide();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao salvar a Sessão: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao Salvar a Sessão");
		}
	}

	public void detalhesPaciente(ActionEvent evento) {
		try {
			this.paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao selecionar o Paciente: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar detalhes do Paciente!");
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			Paciente paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
			PacienteDAO pacienteDAO = new PacienteDAO();
			pacienteDAO.excluir(paciente);

			pacientes = pacienteDAO.listar();
			Messages.addGlobalInfo("Paciente deletado com Sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao excluir Paciente: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Paciente!");
		}
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
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

	public int getAnoCorrente() {
		return anoCorrente;
	}

	public void setAnoCorrente(int anoCorrente) {
		this.anoCorrente = anoCorrente;
	}

}
