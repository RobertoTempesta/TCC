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
import com.roberto.tcc.clinica.domain.Aluno;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAluno")
@ViewScoped
public class AlunoBean implements Serializable {

	private Aluno aluno = null;
	private List<Aluno> alunos = null;
	

	@PostConstruct
	public void init() {
		aluno = new Aluno();
		listarAlunos();
	}

	public void excluir(ActionEvent evento) {
		try {
			Aluno aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");
			AlunoDAO aDAO = new AlunoDAO();
			aDAO.excluir(aluno);
			alunos = aDAO.listar();
			Messages.addGlobalInfo("Aluno excluido com sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao deletar o aluno: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir o Aluno");
		}
	}

	public void listarAlunos() {
		try {
			alunos = new AlunoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao listar os alunos: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Alunos");
		}
	}

	public void detalhesAluno(ActionEvent evento) {
		try {
			this.aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlunoBean.class).log(Level.ERROR, "Erro ao carregar os alunos: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar os detalhes do Aluno");
		}
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

}
