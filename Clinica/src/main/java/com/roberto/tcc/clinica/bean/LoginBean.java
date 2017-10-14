package com.roberto.tcc.clinica.bean;

import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.enumeracao.TipoUsuario;

@SuppressWarnings("serial")
@ManagedBean(name = "MBLogin")
@SessionScoped
public class LoginBean implements Serializable {

	private static final Logger logger = LogManager.getLogger(LoginBean.class);
	private Usuario usuario;
	private Usuario usuarioLogado;

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public String getNomeFormatado() {
		if (usuarioLogado.getPessoa().getNome().length() > 16) {
			return usuarioLogado.getPessoa().getNome().substring(0, 16) + ".";
		}
		return usuarioLogado.getPessoa().getNome();
	}

	public void login() {
		try {

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario user = usuarioDAO.autenticar(usuario);

			if (user == null) {
				Messages.addGlobalWarn("Usuário e/ou senha invalido!");
				return;
			} else if (!user.getAtivo()) {
				Messages.addGlobalWarn("Usuário informado está Bloqueado.");
				return;
			}

			this.usuarioLogado = user;
			logger.info("Usuario logado: " + this.usuario.getPessoa().getCPF());
			Faces.redirect("./paginas/privado/inicio.xhtml");

		} catch (IOException | NoSuchAlgorithmException erro) {
			logger.error("Erro ao autenticar usuario: " + erro);
			Messages.addGlobalError("Ocorreu um erro, tente novamente mais tarde.");
		}
	}

	public void logout() {
		try {
			this.usuarioLogado = null;
			Faces.redirect("./paginas/publico/login.xhtml");
		} catch (IOException erro) {
			logger.error("Erro ao deslogar: " + erro);
		}
	}

	public boolean temAcesso() {
		if (usuarioLogado.getTipoUsuario().equals(TipoUsuario.GERENCIADOR)) {
			return true;
		}
		return false;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
