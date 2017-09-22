package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Paciente;
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
	
}
