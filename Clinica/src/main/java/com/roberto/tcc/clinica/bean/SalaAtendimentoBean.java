package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
import com.roberto.tcc.clinica.domain.SalaAtendimento;

@SuppressWarnings("serial")
@ManagedBean(name = "MBSala")
@ViewScoped
public class SalaAtendimentoBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(SalaAtendimentoBean.class);

	private SalaAtendimento sala = null;

	private List<SalaAtendimento> salas = null;

	private SalaAtendimentoDAO salaDAO = null;

	@PostConstruct
	public void inicial() {
		salaDAO = new SalaAtendimentoDAO();
		listar();
	}

	public void listar() {
		try {

			salas = salaDAO.listar("descricao");

		} catch (RuntimeException erro) {
			logger.error("Erro ao listar salas: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as salas.");
		}
	}

	public void salvar() {

		try {

			salaDAO.merge(sala);
			Messages.addGlobalInfo("A sala foi salva com sucesso.");
			listar();
			RequestContext.getCurrentInstance().execute("PF('dlgNovo').hide();");

		} catch (RuntimeException erro) {
			logger.error("Erro ao salvar Sala: " + erro);
			Messages.addGlobalError("Erro ao tentar salvar.");
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			SalaAtendimento sala = (SalaAtendimento) evento.getComponent().getAttributes().get("salaSelecionada");

			salaDAO.excluir(sala);
			listar();
			Messages.addGlobalInfo("Sala excluida com sucesso");

		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir a sala: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a sala");
		}
	}

	public void editar(ActionEvent evento) {

		sala = (SalaAtendimento) evento.getComponent().getAttributes().get("salaSelecionada");

	}
	
	public void novo() {
		sala = new SalaAtendimento();
	}

	public SalaAtendimento getSala() {
		return sala;
	}

	public void setSala(SalaAtendimento sala) {
		this.sala = sala;
	}

	public List<SalaAtendimento> getSalas() {
		return salas;
	}

	public void setSalas(List<SalaAtendimento> salas) {
		this.salas = salas;
	}

}
