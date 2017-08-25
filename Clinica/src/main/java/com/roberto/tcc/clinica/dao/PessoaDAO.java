package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.HibernateUtil;

public class PessoaDAO extends GenericDAO<Pessoa> {

	public Pessoa buscarCPF(Pessoa pessoa) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(pessoa.getClass());
			consulta.add(Restrictions.ilike("CPF", pessoa.getCPF()));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}

}
