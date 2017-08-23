package com.roberto.tcc.clinica.test.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.domain.Cidade;

public class CidadeDAOTest {

	@Test
	public void salvar() {
		
		EstadoDAO eDAO = new EstadoDAO();
		
		Cidade c = new Cidade();
		c.setNome("Santa Cruz do Rio Pardo");
		c.setCEP("18900-000");
		
		c.setEstado(eDAO.buscar(1L));
		
		CidadeDAO cDAO = new CidadeDAO();
		cDAO.salvar(c);
				
	}
	
	@Test
	@Ignore
	public void listar(){
		
		CidadeDAO cDAO = new CidadeDAO();
		List<Cidade> cidades = cDAO.listar();
		
		for(Cidade c : cidades) {
			System.out.println(c);
		}
	}
	
	@Test
	@Ignore
	public void buscar(){
		
		CidadeDAO cDAO = new CidadeDAO();
		Cidade c = cDAO.buscar(1L);
		
		System.out.println(c);
	}
	
	@Test
	@Ignore
	public void delete(){
		
		CidadeDAO cDAO = new CidadeDAO();
		Cidade c = cDAO.buscar(4L);
		
		cDAO.excluir(c);
	}
	
	@Test
	@Ignore	
	public void editar(){
		
		CidadeDAO cDAO = new CidadeDAO();
		Cidade c = cDAO.buscar(3L);
		
		c.setNome("Jacarézinho");
		cDAO.editar(c);
	}
	
}