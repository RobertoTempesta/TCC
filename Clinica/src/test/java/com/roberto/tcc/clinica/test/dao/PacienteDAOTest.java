package com.roberto.tcc.clinica.test.dao;

import java.util.Date;

import org.junit.Test;

import com.roberto.tcc.clinica.dao.PacienteDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Pessoa;

public class PacienteDAOTest {

	
	@Test
	public void salvar() {
		
		PacienteDAO dao = new PacienteDAO();
		PessoaDAO daoP = new PessoaDAO();
		Pessoa p = new Pessoa();
		p = daoP.buscarCPF("35003699875");
		
		System.out.println(p.getNome());
		
		Paciente pa = new Paciente();
		pa.setPessoa(p);
		pa.setOcupacao("asd");
		pa.setConhecimentoCentro("sad");
		pa.setDataCadastro(new Date());
		pa.setEstadoCivil("solteiro");
		pa.setNomeMae("Niuza");
		pa.setNomePai("José");
		dao.salvar(pa);
		
	}
	
}
