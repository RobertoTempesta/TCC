package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Aluno;
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
	
}
