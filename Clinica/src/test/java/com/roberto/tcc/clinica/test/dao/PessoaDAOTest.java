package com.roberto.tcc.clinica.test.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

import com.roberto.tcc.clinica.dao.CidadeDAO;
import com.roberto.tcc.clinica.dao.PessoaDAO;
import com.roberto.tcc.clinica.dao.TelefoneDAO;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Telefone;
import com.roberto.tcc.clinica.util.Constantes;

public class PessoaDAOTest {

	
	@Test
	@Ignore
	public void salvar() throws ParseException {
		Pessoa p = new Pessoa();
		p.setNome("Roberto Junior Tempesta");
		p.setCPF("35003699875");
		p.setNumero("354");
		p.setBairro("Vila Oitenta");
		
		CidadeDAO cidadeDAO = new CidadeDAO();
		p.setCidade(cidadeDAO.buscar(1L));
		
		p.setDataNacimento(new SimpleDateFormat("dd/MM/yyyy").parse("08/08/1995"));
		p.setEmail("robertotempesta88@gmail.com");
		p.setEndereco("Rua José Zacura");
		p.setEscolaridade("Cursando ensino superior");
		p.setIdade(22);
		p.setRG("414616923");
		p.setSexo(Constantes.MASCULINO);
		
		ArrayList<Telefone> ts = new ArrayList<>();
		
		Telefone t = new Telefone();
		t.setNumero("33721122");
		t.setObservacoes("");
		ts.add(t);
		
		Telefone t2 = new Telefone();
		t2.setNumero("997903757");
		ts.add(t2);
		
		p.setTelefones(ts);
		
		TelefoneDAO tDAO = new TelefoneDAO();
		
		
		PessoaDAO pessoaDAO = new PessoaDAO();
//		tDAO.salvar(t);
//		tDAO.salvar(t2);
		pessoaDAO.salvar(p);
	}
	
	@Test
	public void buscar() {
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa p = pessoaDAO.buscar(1L);
		System.out.println(p.getNome());
		
	}
	
	@Test
	@Ignore
	public void deletar() {
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa p = new Pessoa();
		p = pessoaDAO.buscar(6L);
		pessoaDAO.excluir(p);
	}
	
}
