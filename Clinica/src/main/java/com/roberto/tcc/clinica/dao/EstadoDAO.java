package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Estado;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class EstadoDAO extends GenericDAO<Estado>{

public Estado buscarSigla(String estado) {
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Estado.class);
			consulta.add(Restrictions.ilike("sigla", estado));
			Estado resultado =  (Estado) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
}
