package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.omnifaces.util.Messages;

import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.security.Criptografia;

@SuppressWarnings("serial")
@ManagedBean(name = "MBEsqueceuSenha")
@ViewScoped
public class EsqueceuSenhaBean implements Serializable {

	private Usuario usuario = null;
	
	

	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	public void salvar() {
		try {
			UsuarioDAO dao = new UsuarioDAO();
			String senha = this.usuario.getSenha();
			Usuario usuario = dao.buscarUsuario(this.usuario);
			
			if(usuario == null) {
				Messages.addGlobalError("Esse Usuário não existe!");
				return;
			}
			this.usuario = usuario;
			this.usuario.setSenha(senha);
			this.usuario = Criptografia.gerarSenhaCrip(this.usuario, this.usuario.getSenha());
			dao.merge(usuario);
			Messages.addGlobalInfo("Senha salva com Sucesso!");
		} catch (RuntimeException | NoSuchAlgorithmException | UnsupportedEncodingException erro) {
			LogManager.getLogger(AlterarSenhaBean.class).log(Level.ERROR, "Erro ao tentar salvar o usuario: ", erro);
			Messages.addGlobalError("Erro ao tentar salvar a senha do usuario");

		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
