package com.roberto.tcc.clinica.test.dao;

import org.junit.Test;

import com.roberto.tcc.clinica.dao.TelefoneDAO;
import com.roberto.tcc.clinica.domain.Telefone;

public class TelefoneDAOText {

	@Test
	public void salvar() {
		Telefone t = new Telefone();
		t.setNumero("3372-1122");
		t.setObservacoes("");
		
		TelefoneDAO dao = new TelefoneDAO();
		dao.salvar(t);
	
	}
	
}
