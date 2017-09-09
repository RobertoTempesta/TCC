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

import com.roberto.tcc.clinica.dao.CargoDAO;
import com.roberto.tcc.clinica.domain.Cargo;

@SuppressWarnings("serial")
@ManagedBean(name = "MBCargo")
@ViewScoped
public class CargoBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(CargoBean.class);
	private List<Cargo> cargos = null;

	private Cargo cargo = null;

	private CargoDAO cargoDAO = null;

	@PostConstruct
	public void inicial() {
		cargoDAO = new CargoDAO();
		listar();
	}

	public void novo() {
		cargo = new Cargo();
	}

	public void listar() {
		try {
			cargos = cargoDAO.listar("descricao");
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar cargos" + erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os cargos");
		}
	}

	public void salvar() {
		try {
			cargoDAO.merge(cargo);
			listar();
			Messages.addGlobalInfo("Cargo salvo com Sucesso");
			RequestContext.getCurrentInstance().execute("PF('dlgNovo').hide();");
		} catch (RuntimeException erro) {
			logger.error("Erro ao salvar o cargo: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o cargo");
		}
	}

	public void editar(ActionEvent evento) {

		cargo = (Cargo) evento.getComponent().getAttributes().get("cargoSelecionado");

	}

	public void excluir(ActionEvent evento) {
		try {
			Cargo cargo = (Cargo) evento.getComponent().getAttributes().get("cargoSelecionado");
			cargoDAO.excluir(cargo);
			listar();
			Messages.addGlobalInfo("Cargo excluido com Sucesso");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir Cargo: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao excluir o Cargo selecionado");
		}
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

}
