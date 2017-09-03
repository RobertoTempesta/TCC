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

import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(UsuarioBean.class);

	private Usuario usuario = null;
	private Pessoa pessoa = null;

	private UsuarioDAO usuarioDAO = null;
	private PessoaDAO pessoaDAO = null;

	private List<Usuario> usuarios = null;
	private List<Pessoa> pessoas = null;

	private Boolean ativaNovo = Boolean.FALSE;

	@PostConstruct
	public void init() {
		usuarioDAO = new UsuarioDAO();
		pessoaDAO = new PessoaDAO();

		usuario = new Usuario();
		pessoa = new Pessoa();
		carregarTela();
	}

	public void carregarTela() {
		try {
			usuarios = usuarioDAO.listar();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError(
					"Ocorreu um erro interno ao carregar a tela, entre em contato com a Administrador do sistema.");
			logger.error(erro);
		}
	}

	public void salvarUser() {
		try {
			this.usuario.setPessoa(this.pessoa);
			usuario.setAtivo(true);
			usuario.setSalt("123");
			usuarioDAO.salvar(usuario);
			
			usuarios = usuarioDAO.listar();
			
			Messages.addGlobalInfo("Usuário salvo com Sucesso!");
		} catch (RuntimeException erro) {
			logger.error("Erro ao salvar usuario: " + erro);
			Messages.addGlobalError(
					"Ocorreu um erro interno ao salvar o Usuário, entre em contato com a Administrador do sistema.");
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			usuarioDAO.excluir(usuario);

			carregarTela();
			Messages.addGlobalInfo("Usuario removido com sucesso");
		} catch (RuntimeException erro) {
			logger.error("Erro ao excluir: " + erro);
		}
	}

	public void renderiza() {

		if (pessoa != null) {
			ativaNovo = Boolean.TRUE;
		} else {
			ativaNovo = Boolean.FALSE;
		}

	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getAtivaNovo() {
		return ativaNovo;
	}

	public void setAtivaNovo(Boolean ativaNovo) {
		this.ativaNovo = ativaNovo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

}
