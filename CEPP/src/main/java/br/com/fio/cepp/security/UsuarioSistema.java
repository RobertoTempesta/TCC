package br.com.fio.cepp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.fio.cepp.domain.Usuario;
import lombok.Getter;

public class UsuarioSistema extends User {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private Usuario usuario;
	
	public UsuarioSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getCpf(), usuario.getSenha(), authorities);
		this.usuario = usuario;
	}

}
