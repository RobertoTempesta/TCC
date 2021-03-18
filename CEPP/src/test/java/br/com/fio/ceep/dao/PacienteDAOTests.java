package br.com.fio.ceep.dao;

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.github.javafaker.Faker;

import br.com.fio.cepp.dao.PacienteDAO;
import br.com.fio.cepp.domain.Endereco;
import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.domain.FormacaoAcademica;
import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.enumeracao.EstadoCivil;
import br.com.fio.cepp.domain.enumeracao.Sexo;
import br.com.fio.cepp.domain.enumeracao.Situacao;

public class PacienteDAOTests {

	private Faker fakeData;

	private PacienteDAO pacienteDAO;

	@Before
	public void setUp() {
		fakeData = new Faker(new Locale("pt-BR"));
		pacienteDAO = new PacienteDAO();
	}
	
	@Test
	public void inserirNovoPaciente() {
		Paciente paciente = new Paciente();
		//p.setCrp(fakeData.number().digits(5));
		paciente.setDataCadastro(new Date());
		paciente.setEstadoCivil(EstadoCivil.S);
		paciente.setNomeMae("MAria da Silva");
		paciente.setNomePai("José da Silva");
		paciente.setNumeroCaso("1/2021");
		paciente.setOcupacao("Desocupado");
		paciente.setResponsavelNome("Mãe");
		paciente.setSituacao(Situacao.A);
		
		Pessoa p = new Pessoa();
		p.setCpf("66664444455555");
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
		
		paciente.setPessoa(p);

		System.out.println(p.getEndereco());
		Paciente pacienteSalvo = pacienteDAO.merge(paciente);

		assertNotNull(pacienteSalvo);
	}
	
}
