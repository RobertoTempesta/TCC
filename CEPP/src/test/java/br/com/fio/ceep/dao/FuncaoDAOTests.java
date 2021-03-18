package br.com.fio.ceep.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.com.fio.cepp.dao.FuncaoDAO;
import br.com.fio.cepp.domain.Funcao;

public class FuncaoDAOTests {

	private Faker fakeData;

	private FuncaoDAO funcaoDAO;

	@Before
	public void setUp() {
		fakeData = new Faker(new Locale("pt-BR"));
		funcaoDAO = new FuncaoDAO();
	}

	@Test
	public void inserirNovaFuncao() {
		Funcao f = new Funcao();
		f.setDescricao(fakeData.job().title());

		System.out.println(f.getDescricao());
		Funcao funcaoSalva = funcaoDAO.merge(f);

		assertNotNull(funcaoSalva);
	}

	@Test(expected = RuntimeException.class)
	public void inserirFuncaoSemDescricao() {
		Funcao f = new Funcao();
		Funcao funcaoSalva = funcaoDAO.merge(f);

		assertNull(funcaoSalva);
	}
}
