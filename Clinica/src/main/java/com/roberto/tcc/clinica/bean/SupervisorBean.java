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
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.SupervisorDAO;
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
	private Estado estado = null;

	private List<Supervisor> supervisores = null;
	private List<Estado> estados = null;

	public void inicial() {
		iniciarDomain();
	}

	public void iniciarDomain() {
		supervisor = new Supervisor();
		pessoa = new Pessoa();
		endereco = new Endereco();
		estado = new Estado();
	}

	public void telaNovoSupervisor() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("novo_supervisor.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[novo_supervisor]: " + erro);
		}
	}

	public void telaInicial() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("supervisores.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[supervisores]: " + erro);
		}
	}

	public void editar(ActionEvent evento) {
		try {
			this.supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("codigo",
					this.supervisor.getCodigo());

			telaNovoSupervisor();

		} catch (Exception erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void iniciaEdicao() {
		Long codigo = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("codigo");
		if (codigo != null) {
			this.supervisor = new SupervisorDAO().buscar(codigo);
			this.pessoa = supervisor.getPessoa();
			this.endereco = pessoa.getEndereco();
			this.estado =  endereco.getEstado();
		}

	}

	public void salvar() {
		try {
			
			this.endereco.setEstado(this.estado);
			this.pessoa.setEndereco(this.endereco);
			this.supervisor.setPessoa(this.pessoa);
			this.supervisor.setDataCadastro(new Date());
			SupervisorDAO supervisorDAO = new SupervisorDAO();
			supervisorDAO.salvarPessoa(this.supervisor);

			RequestContext.getCurrentInstance().execute("PF('dlgConfirma').show();");

		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao salvar supervisor: " + erro);
			Messages.addGlobalError("Erro ao tentar salvar o Supervisor");
		} 
	}

	public void verificaCPF() {

		try {
			String cpf = pessoa.getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("Informe um CPF válido!");
				return;
			}

			Pessoa pessoa = new PessoaDAO().buscarCPF(this.pessoa.getCPF());
			if (pessoa != null) {
				Supervisor supervisor = new SupervisorDAO().buscarCodigoPes(pessoa.getCodigo());
				if(supervisor != null && this.supervisor.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Supervisor cadastrado no Sistema!");
					this.pessoa = new Pessoa();
					return;
				}
				this.pessoa = pessoa;
				this.endereco = pessoa.getEndereco();
				this.estado = this.endereco.getEstado();
			}else {
				this.pessoa = new Pessoa();
				this.pessoa.setCPF(cpf);
			}

		} catch (RuntimeException erro) {
			logger.error("Erro ao verificar CPF: " + erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o CPF informado!");
		}

	}

	public void carregarCEP() {
		String cep = endereco.getCEP();
		cep = cep.replace(".", "");
		cep = cep.replace("-", "");
		cep = cep.replace("_", "");

		if (cep == null || cep.equals("")) {
			Messages.addGlobalWarn("Digite um CEP válido!");
			return;
		}

		try {
			JSONObject json = new CEPUtil().capturaJson(cep);
			boolean erro = json.isNull("erro");

			if (!erro) {
				Messages.addGlobalWarn("CEP informado não existe!");
				return;
			}

			endereco.setCEP(json.getString("cep"));
			endereco.setBairro(json.getString("bairro"));
			endereco.setRua(json.getString("logradouro"));
			endereco.setCidade(json.getString("localidade"));
			
			estado = new EstadoDAO().buscarSigla(json.getString("uf"));

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
		} catch (Exception erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void carregarEstados() {
		try {
			estados = new EstadoDAO().listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os estados: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void listarSupervisores() {
		try {
			supervisores = new SupervisorDAO().listarOrdenado();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os supervisores: " + erro);
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
			logger.error("Erro ao excluir supervisor: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o supervisor");
		}
	}
	
	public void detalhesSupervisor(ActionEvent evento) {
		try {
			this.supervisor =  (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
			

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			logger.error("Erro ao buscar Supervisor: " + erro);
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
