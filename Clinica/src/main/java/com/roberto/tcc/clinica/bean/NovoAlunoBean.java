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

import com.roberto.tcc.clinica.dao.AlunoDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.FuncaoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.SupervisorDAO;
import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Funcao;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Supervisor;
import com.roberto.tcc.clinica.util.CEPUtil;

@SuppressWarnings("serial")
@ManagedBean(name = "MBNovoAluno")
@ViewScoped
public class NovoAlunoBean implements Serializable {

	private List<Estado> estados = null;
	private List<Funcao> funcoes = null;
	private List<Supervisor> supervisores = null;

	private Aluno aluno = null;

	@PostConstruct
	public void init() {
		aluno = new Aluno();
		aluno.setPessoa(new Pessoa());
		aluno.getPessoa().setEndereco(new Endereco());
		aluno.getPessoa().getEndereco().setEstado(new Estado());
		aluno.setDataCadastro(new Date());

		carregarEstados();
		listarFuncoes();
		listarSupervisores();
		Aluno aluno = (Aluno) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");
		if (aluno != null) {
			this.aluno = aluno;
		}
	}

	public void editar(ActionEvent evento) {
		Aluno aluno = (Aluno) evento.getComponent().getAttributes().get("alunoSelecionado");
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("paciente", aluno);
	}

	public void carregarEstados() {
		try {
			estados = new EstadoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao listar os estados: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void listarFuncoes() {
		try {
			funcoes = new FuncaoDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao listar as funcoes: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar as Funções");
		}
	}

	public void listarSupervisores() {
		try {
			supervisores = new SupervisorDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao listar os Supervisores: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Supervisores");
		}
	}

	public void salvar() {
		try {

			AlunoDAO aDAO = new AlunoDAO();
			aDAO.salvarPessoa(this.aluno);

			RequestContext.getCurrentInstance().execute("PF('dlgConfirma').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao Salvar o Aluno: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o aluno");
		}
	}

	public void verificaCPF() {

		try {
			String cpf = aluno.getPessoa().getCPF();
			cpf = cpf.replace(".", "");
			cpf = cpf.replace("-", "");
			cpf = cpf.replace("_", "");
			if (cpf == null || cpf.equals("")) {
				Messages.addGlobalWarn("Informe um CPF!");
				return;
			}

			Pessoa pessoa = new PessoaDAO().buscarCPF(this.aluno.getPessoa().getCPF());
			if (pessoa != null) {
				Aluno aluno = new AlunoDAO().buscarCodigoPes(pessoa.getCodigo());
				if (aluno != null && this.aluno.getCodigo() == null) {
					Messages.addGlobalWarn("Essa Pessoa já é um Aluno Cadastrado no Sistema!");
					this.aluno.setPessoa(new Pessoa());
					return;
				}
				this.aluno.setPessoa(pessoa);

			}

		} catch (RuntimeException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao verificar o CPF: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao verificar o CPF informado!");
		}

	}

	public void carregarCEP() {

		String cep = aluno.getPessoa().getEndereco().getCEP();
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
			aluno.getPessoa().setEndereco(endereco);

		} catch (MalformedURLException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao carregar o CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		} catch (IOException erro) {
			LogManager.getLogger(NovoAlunoBean.class).log(Level.ERROR, "Erro ao carregar o CEP: ", erro);
			Messages.addGlobalError("Ocorreu um erro interno ao buscar o endereço");
		}
	}
	
	public void calculaIdade() {
		GregorianCalendar dataAtual = new GregorianCalendar();
		GregorianCalendar nascimento = new GregorianCalendar();
		if (aluno.getPessoa().getDataNascimento() == null) {
			return;
		}
		nascimento.setTime(aluno.getPessoa().getDataNascimento());
		int anohj = dataAtual.get(Calendar.YEAR);
		int anoNascimento = nascimento.get(Calendar.YEAR);
		this.aluno.getPessoa().setIdade(new Integer(anohj - anoNascimento));
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<Supervisor> getSupervisores() {
		return supervisores;
	}

	public void setSupervisores(List<Supervisor> supervisores) {
		this.supervisores = supervisores;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

}
