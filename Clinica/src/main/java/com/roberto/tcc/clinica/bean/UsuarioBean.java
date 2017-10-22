package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Usuario;

@ManagedBean(name = "MBUsuario")
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -7249128232412599268L;

	private Usuario usuario = null;

	private List<Usuario> usuarios = null;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		listarUsuarios();
	}

	public void excluir(ActionEvent evento) {
		try {
			Usuario usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioDAO.excluir(usuario);

			usuarios = usuarioDAO.listar();
			Messages.addGlobalInfo("Usuário deletado com Sucesso");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao excluir Usuario: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar deletar o Usuário");
		}
	}

	public void listarUsuarios() {
		try {
			usuarios = new UsuarioDAO().listar();
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao listar os Usuarios: ", erro);
		}
	}

	public void detalhesUsuario(ActionEvent evento) {
		try {
			this.usuario = (Usuario) evento.getComponent().getAttributes().get("usuarioSelecionado");

			RequestContext.getCurrentInstance().execute("PF('dlgDetalhes').show();");
		} catch (RuntimeException erro) {
			LogManager.getLogger(PacienteBean.class).log(Level.ERROR, "Erro ao carregar os dados do Usuario: ", erro);
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar os detalhes do Usuário");
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
