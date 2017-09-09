package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EnderecoDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.BuscaCEP;

@SuppressWarnings("serial")
@ManagedBean(name = "MBPessoa")
@ViewScoped
public class PessoaBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(PessoaBean.class);

	private Pessoa pessoa = null;
	private Estado estado = null;
	private Cidade cidade = null;
	private Endereco endereco = null;

	private List<Estado> estados = null;
	private List<Pessoa> pessoas = null;

	private CidadeDAO cidadeDAO = null;
	private EstadoDAO estadoDAO = null;
	private PessoaDAO pessoaDAO = null;
	private EnderecoDAO enderecoDAO = null;

	@PostConstruct
	public void init() {
		estado = new Estado();
		pessoa = new Pessoa();
		cidade = new Cidade();
		endereco = new Endereco();


		cidadeDAO = new CidadeDAO();
		pessoaDAO = new PessoaDAO();
		estadoDAO = new EstadoDAO();
		enderecoDAO = new EnderecoDAO();

		carregaTela();
	}

	public void carregaTela() {
		try {
			estados = estadoDAO.listar();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			logger.error("Ocorreu um erro ao carregar dados da tela: " + erro);
		}
	}

	public void buscaCEP() {

		if (this.endereco.getCEP() == null || this.endereco.getCEP().equals("")) {
			return;
		}
		BuscaCEP busca = new BuscaCEP();
		try {

			Endereco endereco = new Endereco();
			endereco = busca.buscaEndereco(this.endereco.getCEP());

			if (endereco == null) {
				Messages.addGlobalWarn("CEP Invalido!");
				return;
			}

			this.estado = estadoDAO.buscarSigla(endereco.getCidade().getEstado());

			Cidade cidade = new Cidade();
			String nome = endereco.getCidade().getNome();
			cidade.setNome(nome);
			cidade = cidadeDAO.buscarNome(cidade);

			if (cidade == null) {
				cidade = new Cidade();
				cidade.setNome(nome);
				cidade.setEstado(estado);
				cidadeDAO.salvar(cidade);
			}

			this.cidade = cidade;
			this.endereco = endereco;

		} catch (IOException | RuntimeException erro) {
			logger.error(erro);
		}
	}

	public void salvarPessoa() {

		try {
			this.cidade.setEstado(this.estado);
			this.endereco.setCidade(this.cidade);
			
			enderecoDAO.salvar(this.endereco);
			
			this.pessoa.setEndereco(this.endereco);
			
			String nomePessoa = this.pessoa.getNome().toUpperCase();
			this.pessoa.setNome(nomePessoa);
			pessoaDAO.salvar(pessoa);
			Messages.addGlobalInfo("Pessoa salva com Sucesso!");
			init();
			
		} catch (RuntimeException erro) {
			logger.error(erro);
			Messages.addFlashGlobalError(
					"Ocorreu um Erro ao salvar o Pessoa, entre em contato com administradora do sistema.");
		}
	}

	public void excluir(ActionEvent evento){
		try {
			Pessoa pessoa =  (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");

			pessoaDAO.excluir(pessoa);
			
			pessoas = pessoaDAO.listar();

			Messages.addGlobalInfo("Pessoa removida com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover a cidade");
			erro.printStackTrace();
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	
	

}
