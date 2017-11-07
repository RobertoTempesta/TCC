package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.domain.Sessao;

@SuppressWarnings("serial")
@ManagedBean(name = "MBHistorico")
@ViewScoped
public class HistoricoBean implements Serializable {

	private List<Sessao> sessoes = null;

	@PostConstruct
	public void init() {
		try {
			sessoes = new SessaoDAO().listar("dataInicio");
		} catch (RuntimeException erro) {
			LogManager.getLogger().log(Level.ERROR, "Erro ao carregar as Sessões", erro);
			Messages.addGlobalError("Erro ao carregar as Sessões!");
		}

	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}
}
