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

import com.roberto.tcc.clinica.dao.FuncaoDAO;
import com.roberto.tcc.clinica.domain.Funcao;

@SuppressWarnings("serial")
@ManagedBean(name = "MBFuncao")
@ViewScoped
public class FuncaoBean implements Serializable {

	private List<Funcao> funcoes = null;

	private Funcao funcao = null;

	private FuncaoDAO funcaoDAO = null;

	@PostConstruct
	public void inicial() {
		funcaoDAO = new FuncaoDAO();
		listar();
	}

	public void novo() {
		funcao = new Funcao();
	}

	public void listar() {
		try {
			funcoes = funcaoDAO.listar("descricao");
		} catch (RuntimeException erro) {
			LogManager.getLogger(FuncaoBean.class.getName()).log(Level.ERROR, "Erro ao listar as Funções", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar as Funções");
		}
	}

	public void salvar() {
		try {
			funcaoDAO.merge(funcao);
			listar();
			Messages.addGlobalInfo("Função salva com Sucesso");
			RequestContext.getCurrentInstance().execute("PF('dlgNovo').hide();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(FuncaoBean.class.getName()).log(Level.ERROR, "Erro ao tentar salvar a Função", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a Função");
		}
	}

	public void editar(ActionEvent evento) {

		funcao = (Funcao) evento.getComponent().getAttributes().get("funcaoSelecionado");

	}

	public void excluir(ActionEvent evento) {
		try {
			Funcao funcao = (Funcao) evento.getComponent().getAttributes().get("funcaoSelecionado");
			funcaoDAO.excluir(funcao);
			listar();
			Messages.addGlobalInfo("Cargo excluido com Sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(FuncaoBean.class.getName()).log(Level.ERROR, "Erro ao tentar Excluir a Fução", erro);
			Messages.addGlobalError("Ocorreu um erro ao excluir a Função selecionado");
		}
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

}
