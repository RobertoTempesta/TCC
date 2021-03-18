package br.com.fio.ceep.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import br.com.fio.cepp.dao.UsuarioDAO;
import br.com.fio.cepp.domain.Usuario;

public class UsuarioDAOTests {

	//private Faker fakeData;

	private UsuarioDAO usuarioDAO;

	@Before
	public void setUp() {
		//fakeData = new Faker(new Locale("pt-BR"));
		usuarioDAO = new UsuarioDAO();
	}
	
	@Test
	public void inserirNovo() {
		try {
			Usuario usuario = usuarioDAO.buscarPorCpf("1");
			assertNotNull(usuario);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
}
