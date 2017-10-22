package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
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

@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoSupervisor")
@ViewScoped
public class NovoSupervisorBean implements Serializable {

	private List<Estado> estados = null;

	private Supervisor supervisor = null;

	@PostConstruct
	public void init() {
		supervisor = new Supervisor();
		supervisor.setPessoa(new Pessoa());
		supervisor.getPessoa().setEndereco(new Endereco());
		supervisor.getPessoa().getEndereco().setEstado(new Estado());
		supervisor.setDataCadastro(new Date());

		carregarEstados();
		Supervisor superv = (Supervisor) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get("supervisor");
		if (superv != null) {
			this.supervisor = superv;
		}
	}

	public void carregarEstados() {
		try {
			estados = new EstadoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoSupervisorBean.class).log(Level.ERROR, "Erro ao listar os estados: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void editar(ActionEvent evento) {
		Supervisor supervisor = (Supervisor) evento.getComponent().getAttributes().get("supervisorSelecionado");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("supervisor", supervisor);
	}

	public void calculaIdade() {
		GregorianCalendar dataAtual = new GregorianCalendar();
		GregorianCalendar nascimento = new GregorianCalendar();
		if (supervisor.getPessoa().getDataNascimento() == null) {
			return;
		}
		nascimento.setTime(supervisor.getPessoa().getDataNascimento());
		int anohj = dataAtual.get(Calendar.YEAR);
		int anoNascimento = nascimento.get(Calendar.YEAR);
		this.supervisor.getPessoa().setIdade(new Integer(anohj - anoNascimento));
	}

	public void salvar() {
		try {

			SupervisorDAO supervisorDAO = new SupervisorDAO();
			supervisorDAO.salvarPessoa(this.supervisor);

			RequestContext.getCurrentInstance().execute("PF('dlgConfirma').show();");

		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoSupervisorBean.class).log(Level.ERROR, "Erro ao Salvar o Supervisor: ", erro);
			Messages.addGlobalError("Erro ao tentar Salvar o Supervisor");
		}
	}

	public void verificaCPF() {

		try {
			String cpf = supervisor.getPessoa().getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("Informe um CPF válido!");
				return;
			}

			Pessoa pessoa = new PessoaDAO().buscarCPF(this.supervisor.getPessoa().getCPF());
			if (pessoa != null) {
				Supervisor supervisor = new SupervisorDAO().buscarCodigoPes(pessoa.getCodigo());
				if (supervisor != null && this.supervisor.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Supervisor cadastrado no Sistema!");
					this.supervisor.setPessoa(new Pessoa());
					return;
				}
				this.supervisor.setPessoa(pessoa);

			}

		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoSupervisorBean.class).log(Level.ERROR, "Erro ao verificar o CPF: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o CPF informado!");
		}

	}

	public void carregarCEP() {
		String cep = supervisor.getPessoa().getEndereco().getCEP();
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
			Endereco endereco = new Endereco();
			endereco.setCEP(json.getString("cep"));
			endereco.setBairro(json.getString("bairro"));
			endereco.setRua(json.getString("logradouro"));
			endereco.setCidade(json.getString("localidade"));

			endereco.setEstado(new EstadoDAO().buscarSigla(json.getString("uf")));
			
			supervisor.getPessoa().setEndereco(endereco);

		} catch (MalformedURLException erro) {
			LogManager.getLogger(NovoSupervisorBean.class).log(Level.ERROR, "Erro ao buscar CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			LogManager.getLogger(NovoSupervisorBean.class).log(Level.ERROR, "Erro ao buscar CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

}
