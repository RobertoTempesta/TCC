package com.roberto.tcc.clinica.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class GenericDAO<Entidade> implements Serializable{

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();

		} catch (RuntimeException exception) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Entidade> listar() throws RuntimeException{
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			Criteria consulta = sessao.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
			
		}catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo) throws RuntimeException{
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade) consulta.uniqueResult();
			return resultado;
			
		}catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

	public void excluir(Entidade entidade) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();

		} catch (RuntimeException exception) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw exception;
		} finally {
			sessao.close();
		}
		
	}
	
	public void editar(Entidade entidade) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {

			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();

		} catch (RuntimeException exception) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw exception;
		} finally {
			sessao.close();
		}
		
	}

}
