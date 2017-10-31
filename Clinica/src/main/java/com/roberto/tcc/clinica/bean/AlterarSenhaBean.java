package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.security.Criptografia;

@SuppressWarnings("serial")
@ManagedBean(name = "MBAlteraSenha")
@ViewScoped
public class AlterarSenhaBean implements Serializable {

	private Usuario usuario = null;

	@ManagedProperty(value = "#{MBLogin}")
	private LoginBean loginBean;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		carregarDados();
	}

	public void carregarDados() {
		try {
			usuario = new UsuarioDAO().buscar(loginBean.getUsuarioLogado().getCodigo());
		} catch (RuntimeException erro) {
			LogManager.getLogger(AlterarSenhaBean.class).log(Level.ERROR, "Erro ao tentar salvar o usuario: ", erro);
			Messages.addGlobalError("Erro ao carregar os dados do Usuário");
		}
	}

	public void salvar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();

			usuario = Criptografia.gerarSenhaCrip(usuario, usuario.getSenha());

			dao.merge(usuario);
			Messages.addGlobalInfo("Senha salva com Sucesso!");
		} catch (RuntimeException | NoSuchAlgorithmException | UnsupportedEncodingException erro) {
			LogManager.getLogger(AlterarSenhaBean.class).log(Level.ERROR, "Erro ao tentar salvar o usuario: ", erro);
			Messages.addGlobalError("Erro ao tentar salvar a senha do Usuário");
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
