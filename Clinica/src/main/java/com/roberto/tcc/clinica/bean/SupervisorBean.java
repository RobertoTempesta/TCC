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

import com.roberto.tcc.clinica.dao.SupervisorDAO;
import com.roberto.tcc.clinica.domain.Supervisor;

@SuppressWarnings("serial")
@ManagedBean(name = "MBSupervisor")
@ViewScoped
public class SupervisorBean implements Serializable {

	private Supervisor supervisor = null;

	private List<Supervisor> supervisores = null;

	@PostConstruct
	public void iniciarDomain() {
		supervisor = new Supervisor();
		listarSupervisores();
	}

	public void listarSupervisores() {
		try {
			supervisores = new SupervisorDAO().listarOrdenado();
		} catch (RuntimeException erro) {
			LogManager.getLogger(SupervisorBean.class).log(Level.ERROR, "Erro ao listar os Supervisores: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Supervisores");
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			Supervisor supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
			SupervisorDAO supervisorDAO = new SupervisorDAO();
			supervisorDAO.excluir(supervisor);

			supervisores = supervisorDAO.listarOrdenado();
			Messages.addGlobalInfo("Supervisor foi deletado com Sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(SupervisorBean.class).log(Level.ERROR, "Erro ao Excluir o Supervisor: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o supervisor");
		}
	}

	public void detalhesSupervisor(ActionEvent evento) {
		try {
			this.supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(SupervisorBean.class).log(Level.ERROR, "Erro ao carregar os detalhes Supervisores: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar os detalhes do Supervisor");
		}
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public List<Supervisor> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Supervisor> supervisores) {
		this.supervisores = supervisores;
	}

}
