package br.com.fio.cepp.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.fio.cepp.dao.UsuarioDAO;
import br.com.fio.cepp.domain.Usuario;
import br.com.fio.cepp.enumeracao.MensagensEnum;
import br.com.fio.cepp.service.NegocioException;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = null;
		UsuarioSistema user = null;
		
		try {
			usuario = usuarioDAO.buscarPorCpf(cpf);
		} catch (RuntimeException err) {
			throw new NegocioException(MensagensEnum.MSG_ERRO_NERGOCIO.getMsg());
		}
		
		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(usuario.getTipoUsuario().toString()));
		
		return authorities;
	}

}
