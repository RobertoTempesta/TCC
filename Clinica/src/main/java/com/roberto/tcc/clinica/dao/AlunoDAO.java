package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Aluno;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class AlunoDAO extends GenericDAO<Aluno>{

	public Aluno buscarCodigoPes(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Aluno.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Aluno resultado = (Aluno) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}
	
	public Aluno salvarPessoa(Aluno aluno) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			
			transacao = sessao.beginTransaction();
			aluno.getPessoa().setEndereco((Endereco) sessao.merge(aluno.getPessoa().getEndereco()));
			aluno.setPessoa((Pessoa) sessao.merge(aluno.getPessoa()));
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
	
}
