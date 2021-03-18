package br.com.fio.cepp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

import java.util.ArrayList;
import java.util.List;
import br.com.fio.cepp.domain.Aluno;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.util.HibernateUtil;

public class AlunoDAO extends GenericDAO<Aluno> {

	private static final long serialVersionUID = 1594053439789726437L;
	
	public Aluno merge(Aluno aluno) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		
		List<Telefone> telefones = new ArrayList<>();
		List<Telefone> telefonesSalvos = new ArrayList<>();

		try {
			telefones = aluno.getPessoa().getTelefones();
			aluno.getPessoa().setTelefones(new ArrayList<>());
			
			transacao = sessao.beginTransaction();
			
			Pessoa pessoa = (Pessoa) sessao.merge(aluno.getPessoa());
					
			for (Telefone tel : telefones) {
				tel.setPessoa(pessoa);
				tel = (Telefone) sessao.merge(tel);
				telefonesSalvos.add(tel);
			}
			
			pessoa.setTelefones(telefonesSalvos);
			aluno.setPessoa(pessoa);
			
			Aluno retorno = (Aluno) sessao.merge(aluno);
			
			transacao.commit();
			return retorno;
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	public Number getNumeroAlunos() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Aluno.class);
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
}
