package com.roberto.tcc.clinica.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Pessoa;
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

	public Supervisor buscarCodigoPes(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Supervisor.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Supervisor resultado = (Supervisor) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}
	
	public Supervisor salvarPessoa(Supervisor supervisor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			
			transacao = sessao.beginTransaction();
			supervisor.getPessoa().setEndereco((Endereco) sessao.merge(supervisor.getPessoa().getEndereco()));
			supervisor.setPessoa((Pessoa) sessao.merge(supervisor.getPessoa()));
			Supervisor retorno = (Supervisor) sessao.merge(supervisor);
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
