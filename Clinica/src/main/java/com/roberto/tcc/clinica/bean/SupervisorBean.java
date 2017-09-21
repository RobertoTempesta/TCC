package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.SupervisorDAO;
import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Supervisor;
import com.roberto.tcc.clinica.util.CEPUtil;

@ManagedBean(name = "MBSupervisor")
@ViewScoped
public class SupervisorBean implements Serializable {

	private static final long serialVersionUID = -3270976233702785391L;

	private static final Logger logger = LogManager.getLogger(SupervisorBean.class);

	private Supervisor supervisor = null;
	private Pessoa pessoa = null;
	private Endereco endereco = null;
	private Cidade cidade = null;
	private Estado estado = null;

	private List<Supervisor> supervisores = null;
	private List<Estado> estados = null;

	private SupervisorDAO supervisorDAO = null;
	private PessoaDAO pessoaDAO = null;
	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;

	public void inicial() {
		iniciarDAO();
		iniciarDomain();
	}

	public void iniciarDomain() {
		supervisor = new Supervisor();
		pessoa = new Pessoa();
		endereco = new Endereco();
		cidade = new Cidade();
		estado = new Estado();
	}

	public void iniciarDAO() {
		supervisorDAO = new SupervisorDAO();
		estadoDAO = new EstadoDAO();
		cidadeDAO = new CidadeDAO();
		pessoaDAO = new PessoaDAO();
	}

	public void telaNovoSupervisor() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("novo_supervisor.xhtml");
	}

	public void telaInicial() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("supervisores.xhtml");
	}

	public void editar(ActionEvent evento) {
		try {
			this.supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("codigo",
					this.supervisor.getCodigo());

			telaNovoSupervisor();

		} catch (IOException erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void iniciaEdicao() {
		Long codigo = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("codigo");
		if (codigo != null) {
			this.supervisor = supervisorDAO.buscar(codigo);
			this.pessoa = supervisor.getPessoa();
			this.endereco = pessoa.getEndereco();
			this.cidade = endereco.getCidade();
			this.estado = cidade.getEstado();
		}

	}

	public void salvar() {
		try {
			this.cidade.setEstado(this.estado);
			this.endereco.setCidade(cidade);
			this.pessoa.setEndereco(endereco);
			this.pessoa = pessoaDAO.salvarPesEndereco(this.pessoa);
			this.supervisor.setPessoa(pessoa);
			this.supervisor.setDataCadastro(new Date());
			supervisorDAO.merge(this.supervisor);

			Messages.addGlobalInfo("Supervisor salvo com Sucesso!");

			telaInicial();
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao salvar supervisor: " + erro);
			Messages.addGlobalError("Erro ao tentar salvar o Supervisor");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar voltar para a tela inicial");
			Messages.addGlobalError("Erro ao sair da tela de cadastro");
		}
	}

	public void verificaCPF() {

		try {
			String cpf = pessoa.getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("'CPF' invalido");
				return;
			}

			Pessoa pessoa = pessoaDAO.buscarCPF(this.pessoa.getCPF());
			if (pessoa != null) {
				this.pessoa = pessoa;
				this.endereco = pessoa.getEndereco();
				this.cidade = this.endereco.getCidade();
				this.estado = this.cidade.getEstado();
			}

		} catch (RuntimeException erro) {
			logger.error("Erro ao verificar CPF: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o 'CPF'");
		}

	}

	public void carregarCEP() {
		String cep = endereco.getCEP();
		cep = cep.replace(".", "");
		cep = cep.replace("-", "");
		cep = cep.replace("_", "");

		if (cep == null || cep.equals("")) {
			Messages.addGlobalWarn("Digite um CEP");
			return;
		}

		try {
			JSONObject json = new CEPUtil().capturaJson(cep);
			boolean erro = json.isNull("erro");

			if (!erro) {
				Messages.addGlobalWarn("Esse 'CEP' não existe");
				return;
			}

			endereco.setCEP(json.getString("cep"));
			endereco.setBairro(json.getString("bairro"));
			endereco.setRua(json.getString("logradouro"));

			String nomeCidade = json.getString("localidade");
			cidade = cidadeDAO.buscarNome(nomeCidade);
			if (cidade == null) {
				cidade = new Cidade();
				cidade.setNome(nomeCidade);
			}
			estado = estadoDAO.buscarSigla(json.getString("uf"));

		} catch (MalformedURLException erro) {
			logger.error("Erro ao buscar o CEP: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			logger.error("Erro ao buscar o CEP: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}

	public void novo() {

		try {
			telaNovoSupervisor();
		} catch (IOException erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void carregarEstados() {
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

			supervisores = supervisorDAO.listarOrdenado();
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
