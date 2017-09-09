package com.roberto.tcc.clinica.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.roberto.tcc.clinica.domain.Supervisor;
import com.roberto.tcc.clinica.util.HibernateUtil;


@SuppressWarnings("serial")
public class SupervisorDAO extends GenericDAO<Supervisor>{
	
	@SuppressWarnings("unchecked")
	public List<Supervisor> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Supervisor.class);
			consulta.createAlias("pessoa", "p");
			consulta.addOrder(Order.asc("p.nome"));
			List<Supervisor> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

}
