package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class CidadeDAO extends GenericDAO<Cidade>{

	public Cidade buscarNome(String cidade) {
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.ilike("nome", cidade));
			Cidade resultado =  (Cidade) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

}
