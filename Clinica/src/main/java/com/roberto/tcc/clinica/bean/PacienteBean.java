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

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.CEPUtil;

@ManagedBean(name = "MBPaciente")
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 3395759380539498382L;

	private static final Logger logger = LogManager.getLogger(PacienteBean.class);

	private Paciente paciente = null;
	private Pessoa pessoa = null;
	private Endereco endereco = null;
	private Cidade cidade = null;
	private Estado estado = null;

	private List<Estado> estados = null;
	private List<Paciente> pacientes = null;

	private PacienteDAO pacienteDAO = null;
	private PessoaDAO pessoaDAO = null;
	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;

	public void iniciarDomain() {
		paciente = new Paciente();
		pessoa = new Pessoa();
		endereco = new Endereco();
		cidade = new Cidade();
		estado = new Estado();
	}

	public void iniciarDAO() {
		pacienteDAO = new PacienteDAO();
		pessoaDAO = new PessoaDAO();
		cidadeDAO = new CidadeDAO();
		estadoDAO = new EstadoDAO();
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
	
	public void detalhesPaciente(ActionEvent evento) {
		try {
			this.paciente =  (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
			pacienteDAO.buscar(paciente.getCodigo());

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir Paciente: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Paciente");
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

	public void carregarEstados() {
		try {
			estados = estadoDAO.listar();
		} catch (RuntimeException erro) {
			logger.error("Erro ao listar os estados: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao listar os Estados");
		}
	}

	public void telaNovoPaciente() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("novo_paciente.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[novo_usuario]: " + erro);
		}
	}

	public void telaInicial() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("pacientes.xhtml");
		} catch (IOException erro) {
			logger.error("Ocorreu um erro ao tentar redirecionar a tela[pacientes] " + erro);
		}
	}

	public void inicial() {
		iniciarDAO();
		iniciarDomain();
	}

	public void carregarPacientes() {
		try {
			pacientes = pacienteDAO.listar();
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar carregar a lista de pacientes: " + erro);
		}
	}

	public void salvar() {
		try {

			this.cidade.setEstado(this.estado);
			this.endereco.setCidade(this.cidade);
			this.pessoa.setEndereco(this.endereco);
			this.pessoa = pessoaDAO.salvarPesEndereco(this.pessoa);
			this.paciente.setPessoa(this.pessoa);
			this.paciente.setDataCadastro(new Date());
			pacienteDAO.merge(this.paciente);
			telaInicial();
			Messages.addGlobalInfo("Paciente salvo com sucesso");
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao tentar salvar o paciente" + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o Paciente");
		}
	}
	
	public void excluir(ActionEvent evento) {
		try {
			Paciente paciente =  (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
			pacienteDAO.excluir(paciente);

			pacientes = pacienteDAO.listar();
			Messages.addGlobalInfo("Paciente deletado com Sucesso");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir Paciente: " + erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Paciente");
		}
	}
	
	public void editar(ActionEvent evento) {
		try {
			Paciente paciente =  (Paciente) evento.getComponent().getAttributes().get("pacienteSelecionado");
			FacesContext.getCurrentInstance().getExternalContext().getFlash().put("codigo",
					paciente.getCodigo());

			telaNovoPaciente();

		} catch (Exception erro) {
			logger.error("Erro ao direcionar pagina: " + erro);
		}
	}

	public void iniciaEdicao() {
		Long codigo = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("codigo");
		if (codigo != null) {
			this.paciente = pacienteDAO.buscar(codigo);
			this.pessoa = paciente.getPessoa();
			this.endereco = pessoa.getEndereco();
			this.cidade = endereco.getCidade();
			this.estado = cidade.getEstado();
		}

	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
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

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}

}
