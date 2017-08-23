package com.roberto.tcc.clinica.test.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.roberto.tcc.clinica.dao.EstadoDAO;
import com.roberto.tcc.clinica.domain.Estado;

public class EstadoDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		Estado estado = new Estado();
		estado.setNome("São Paulo");
		estado.setSigla("SP");
		
		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
	}
	
	@Test
	public void listar(){
		
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> estados = estadoDAO.listar();
		
		for(Estado e : estados) {
			System.out.println(e);
		}
	}
	
	@Test
	@Ignore
	public void buscar(){
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado e = estadoDAO.buscar(5L);
		
		System.out.println(e);
	}
	
	@Test
	@Ignore
	public void delete(){
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado e = estadoDAO.buscar(4L);
		
		estadoDAO.excluir(e);
	}
	
	@Ignore
	@Test
	public void editar(){
		
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado e = estadoDAO.buscar(3L);
		
		e.setNome("Paraná");
		estadoDAO.editar(e);
	}
	
}
