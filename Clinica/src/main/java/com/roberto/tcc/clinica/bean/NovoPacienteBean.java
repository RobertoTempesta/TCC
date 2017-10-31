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
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.CEPUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoPaciente")
@ViewScoped
public class NovoPacienteBean implements Serializable {

	private List<Estado> estados = null;

	private Paciente paciente = null;

	@PostConstruct
	public void init() {
		paciente = new Paciente();
		paciente.setPessoa(new Pessoa());
		paciente.getPessoa().setEndereco(new Endereco());
		paciente.getPessoa().getEndereco().setEstado(new Estado());
		paciente.setDataCadastro(new Date());
		carregarEstados();
		Paciente paciente = (Paciente) FacesContext.getCurrentInstance().getExternalContext().getFlash()
				.get("paciente");
		if (paciente != null) {
			this.paciente = paciente;
		}
	}

	public void editar(ActionEvent evento) {
		Paciente paciente = (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("paciente", paciente);
	}

	public void carregarEstados() {
		try {
			estados = new EstadoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro ao listar os estados: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void salvar() {
		try {

			PacienteDAO pacienteDAO = new PacienteDAO();
			paciente.setFaltas_injustificadas(0);
			paciente.setFaltas_justificadas(0);
			paciente.setPresencas(0);
			pacienteDAO.salvarPessoa(this.paciente);
			RequestContext.getCurrentInstance().execute("PF('dlgConfirma').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro ao Salvar o Paciente: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Paciente");
		}
	}

	public void calculaIdade() {
		GregorianCalendar dataAtual = new GregorianCalendar();
		GregorianCalendar nascimento = new GregorianCalendar();
		if (paciente.getPessoa().getDataNascimento() == null) {
			return;
		}
		nascimento.setTime(paciente.getPessoa().getDataNascimento());
		int anohj = dataAtual.get(Calendar.YEAR);
		int anoNascimento = nascimento.get(Calendar.YEAR);
		this.paciente.getPessoa().setIdade(new Integer(anohj - anoNascimento));
	}

	public void verificaCPF() {

		try {
			String cpf = paciente.getPessoa().getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("O CPF informado não é válido!");
				return;
			}

			Pessoa pessoa = new PessoaDAO().buscarCPF(this.paciente.getPessoa().getCPF());
			if (pessoa != null) {
				Paciente paciente = new PacienteDAO().buscarCodigoPes(pessoa.getCodigo());
				if (paciente != null && this.paciente.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Paciente cadastrado no Sistema!");
					this.paciente.setPessoa(new Pessoa());
					return;
				}
				this.paciente.setPessoa(pessoa);
			}

		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro ao verificar CPF: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o 'CPF'");
		}

	}

	public void carregarCEP() {

		String cep = paciente.getPessoa().getEndereco().getCEP();
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

			this.paciente.getPessoa().setEndereco(endereco);

		} catch (MalformedURLException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro na busca do CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			LogManager.getLogger(NovoPacienteBean.class).log(Level.ERROR, "Erro na busca do CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
