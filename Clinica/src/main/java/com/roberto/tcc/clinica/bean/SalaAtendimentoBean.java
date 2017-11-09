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

import com.roberto.tcc.clinica.dao.SalaAtendimentoDAO;
import com.roberto.tcc.clinica.domain.SalaAtendimento;

@SuppressWarnings("serial")
@ManagedBean(name = "MBSala")
@ViewScoped
public class SalaAtendimentoBean implements Serializable {

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
			LogManager.getLogger(SalaAtendimentoBean.class.getName()).log(Level.ERROR, "Erro ao listar as Salas", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as salas.");
		}
	}

	public void salvar() {

		try {

			salaDAO.merge(sala);
			Messages.addGlobalInfo("A Sala foi salva com sucesso.");
			listar();
			RequestContext.getCurrentInstance().execute("PF('dlgNovo').hide();");

		} catch (RuntimeException erro) {
			LogManager.getLogger(SalaAtendimentoBean.class.getName()).log(Level.ERROR, "Erro ao tentar salvar", erro);
			Messages.addGlobalError("Erro ao tentar salvar a Sala.");
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			SalaAtendimento sala = (SalaAtendimento) evento.getComponent().getAttributes().get("salaSelecionada");

			salaDAO.excluir(sala);
			listar();
			Messages.addGlobalInfo("Sala excluida com sucesso");

		} catch (RuntimeException erro) {
			LogManager.getLogger(SalaAtendimentoBean.class.getName()).log(Level.ERROR, "Erro ao tentar excluir a Sala",
					erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir a Sala");
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
