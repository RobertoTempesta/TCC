package br.com.fio.ceep.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.com.fio.cepp.dao.SupervisorDAO;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.domain.enumeracao.Sexo;

public class SupervidorDAOTests {

	private Faker fakeData;

	private SupervisorDAO supervisorDAO;

	@Before
	public void setUp() {
		fakeData = new Faker(new Locale("pt-BR"));
		supervisorDAO = new SupervisorDAO();
	}
	
	@Test
	public void inserirNovo() {
		Supervisor s = new Supervisor();
		s.setCrp(fakeData.number().digits(5));
		s.setDataCadastro(new Date());
		
		Pessoa p = new Pessoa();
		p.setCpf("00000000000000");
		p.setDataNascimento(new Date());
		//p.setEmail("email@email.com");
		
		Endereco e = new Endereco();
				
		Estado es = new Estado();
		es.setCodigo(53L);
		
		e.setEstado(es);
		
		e.setCep(fakeData.address().zipCode());
		e.setRua(fakeData.address().streetName());
		e.setNumero(fakeData.address().streetAddressNumber());
		e.setBairro(fakeData.address().firstName());
		e.setCidade(fakeData.address().cityName());
				
		p.setEndereco(e);
		
		FormacaoAcademica fo = new FormacaoAcademica();
		fo.setCodigo(2L);
		
		p.setFormacaoAcademica(fo);
		
		p.setIdade(20);
		p.setNome(fakeData.name().fullName());
		p.setRg("111111111111");
		p.setSexo(Sexo.F);
		
		s.setPessoa(p);

		System.out.println(p.getEndereco());
		Supervisor supSalvo = supervisorDAO.merge(s);

		assertNotNull(supSalvo);
	}
	
}
