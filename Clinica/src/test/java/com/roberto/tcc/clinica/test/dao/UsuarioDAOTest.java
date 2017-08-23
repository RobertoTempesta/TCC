package com.roberto.tcc.clinica.test.dao;

import org.junit.Test;

import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.UsuarioDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;

public class UsuarioDAOTest {

	@Test
	public void salvar() {
		Usuario u = new Usuario();
		UsuarioDAO dao = new UsuarioDAO();
		Pessoa p = new Pessoa();
		PessoaDAO pDao = new PessoaDAO();
		p = pDao.buscar(1L);
		u.setPessoa(p);
		u.setAtivo(true);
		u.setSenha("123");
		u.setSalt("abc");
		u.setTipo('G');
		
		dao.salvar(u);
	}
}
