package com.roberto.tcc.clinica.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;

@SuppressWarnings("serial")
@ManagedBean(name="MBUsuario")
@ViewScoped
public class UsuarioBean implements Serializable{

	private static final Logger logger = LogManager.getLogger(UsuarioBean.class);
	private Usuario usuario = null;
	private Pessoa pessoa = null;
	private UsuarioDAO usuarioDAO = null;
	private List<Usuario> usuarios = null;
	private String sexo = null;
	
	@PostConstruct
	public void init() {
		usuarioDAO = new UsuarioDAO();
		usuario = new Usuario();
		pessoa = new Pessoa();
		listarUsuarios();
	}

	private void listarUsuarios() {
		try {
			usuarios = usuarioDAO.listar();
		}catch(RuntimeException erro) {
			logger.error(erro);
		}
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
