package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.domain.Estado;

@SuppressWarnings("serial")
@ManagedBean(name = "MBEstado")
@ViewScoped
public class EstadoBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(EstadoBean.class);
	private Estado estado;
	private List<Estado> estados;
	private EstadoDAO estadoDAO;

	@PostConstruct
	public void init() {
		estado = new Estado();
		estadoDAO = new EstadoDAO();
		listarEstado();
	}

	private void listarEstado() {
		try {
			estados = estadoDAO.listar();
		} catch (RuntimeException exception) {
			logger.error("Listar Estados - Erro: " + exception);
		}
		
	}

	public void salvar() {
		try {
			if (this.estado != null || this.estado.getNome() != null || this.estado.getSigla() != null) {
				estadoDAO.salvar(estado);
				estado = new Estado();
				listarEstado();
			} else {
				Messages.addGlobalWarn("Preencha todos os campos!");
			}
		} catch (RuntimeException exception) {
			logger.error("Salvar Estado - Erro: " + exception);
			Messages.addGlobalError(
					"Ocorreu um erro ao salvar o estado, entre em contato com a Administradora do Sistema");
		}

	}
	
	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);
			
			estados = estadoDAO.listar();

			Messages.addGlobalInfo("Estado removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o estado");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(ArrayList<Estado> estados) {
		this.estados = estados;
	}

}
