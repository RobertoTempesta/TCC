package br.com.fio.ceep.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.com.fio.cepp.dao.AlunoDAO;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Funcao;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.domain.enumeracao.Sexo;

public class AlunoDAOTests {

	private Faker fakeData;

	private AlunoDAO alunoDAO;

	@Before
	public void setUp() {
		fakeData = new Faker(new Locale("pt-BR"));
		alunoDAO = new AlunoDAO();
	}
	
	@Test
	public void inserirNovoPaciente() {
		Aluno aluno = new Aluno();
		//p.setCrp(fakeData.number().digits(5));
		aluno.setDataCadastro(new Date());
		Funcao f = new Funcao();
		f.setCodigo(1L);
		aluno.setFuncao(f);
		aluno.setRa("321456521478");
		
		Supervisor s = new Supervisor();
		s.setCodigo(1L);
		
		aluno.setSupervisor(s);
		
		Pessoa p = new Pessoa();
		p.setCpf("321456521478");
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
		p.setSexo(Sexo.M);
		
		aluno.setPessoa(p);

		System.out.println(p.getEndereco());
		Aluno alunoSalvo = alunoDAO.merge(aluno);

		assertNotNull(alunoSalvo);
	}
}
