package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class EnderecoDAO extends GenericDAO<Endereco>{

public Endereco buscarCEP(String CEP) {
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Endereco.class);
			consulta.add(Restrictions.ilike("CEP", CEP));
			Endereco resultado =   (Endereco) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
