package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EnderecoDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.SupervisorDAO;
import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Supervisor;
import com.roberto.tcc.clinica.util.BuscaCEP;

@SuppressWarnings("serial")
@ManagedBean(name = "MBSupervisor")
@ViewScoped
public class SupervisorBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(SupervisorBean.class);

	private Supervisor supervisor = null;
	private Pessoa pessoa = null;
	private Endereco endereco = null;
	private Cidade cidade = null;
	private Estado estado = null;

	private List<Supervisor> supervisores = null;
	// private List<Cidade> cidades = null;
	private List<Estado> estados = null;

	private SupervisorDAO supervisorDAO = null;
	private PessoaDAO pessoaDAO = null;
	private EnderecoDAO enderecoDAO = null;
	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;

	@PostConstruct
	public void inicial() {
		supervisorDAO = new SupervisorDAO();
	}
	
	public void buscarCEP() {
		if(endereco == null || endereco.getCEP() == null || endereco.getCEP().equals("")) {
			Messages.addGlobalWarn("Digite um CEP");
			return;
		}
		
		try {
			BuscaCEP busca = new BuscaCEP();
			Endereco endereco = busca.buscaEndereco(this.endereco.getCEP());
			if(endereco == null) {
				Messages.addGlobalWarn("Digite um CEP valido");
				return;
			}
			this.endereco = endereco;
		}catch(IOException erro) {
			logger.error("Erro ao buscar o CEP: "+erro);
		}
	}

	public void novo() {
		supervisor = new Supervisor();
		pessoa = new Pessoa();
		endereco = new Endereco();
		cidade = new Cidade();
		estado = new Estado();
		
		estadoDAO = new EstadoDAO();
		listarEstados();
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		try {
			response.sendRedirect("novo_supervisor.xhtml");
			FacesContext.getCurrentInstance().responseComplete();
			
		} catch (IOException erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void listarEstados() {
		try {
			estados = estadoDAO.listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os estados: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void listarSupervisores() {
		try {
			supervisores = supervisorDAO.listarOrdenado();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os supervisores: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os Supervisores");
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			Supervisor supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
			supervisorDAO.excluir(supervisor);
			Messages.addGlobalInfo("Supervisor foi deletado com Sucesso");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir supervisor: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o supervisor");
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

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
