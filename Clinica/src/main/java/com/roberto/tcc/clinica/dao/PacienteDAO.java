package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class PacienteDAO extends GenericDAO<Paciente>{

	public Paciente buscarPessoa(Long codigo) throws RuntimeException{
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			Criteria consulta = sessao.createCriteria(Paciente.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Paciente resultado = (Paciente) consulta.uniqueResult();
			return resultado;
			
		}catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Paciente buscarCodigoPes(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Paciente.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Paciente resultado = (Paciente) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}
	
	public Paciente salvarPessoa(Paciente paciente) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			
			transacao = sessao.beginTransaction();
			paciente.getPessoa().setEndereco((Endereco) sessao.merge(paciente.getPessoa().getEndereco()));
			paciente.setPessoa((Pessoa) sessao.merge(paciente.getPessoa()));
			Paciente retorno = (Paciente) sessao.merge(paciente);
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
	
	public Number buscaNumeroPacientes() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Paciente.class);
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
}
