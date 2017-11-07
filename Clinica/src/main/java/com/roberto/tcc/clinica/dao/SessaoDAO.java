package com.roberto.tcc.clinica.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Sessao;
import com.roberto.tcc.clinica.enumeracao.Frequencia;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class SessaoDAO extends GenericDAO<Sessao>{

	public Sessao salvarPrimeiraSessao(Sessao s) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			s.setPaciente((Paciente) sessao.merge(s.getPaciente()));
			Sessao retorno = (Sessao) sessao.merge(s);
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
	
	@SuppressWarnings("unchecked")
	public List<Sessao> verificaPossibilidade(Sessao s) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(s.getDataInicio());
			calendar.set(Calendar.MINUTE, 0);
			s.setDataInicio(calendar.getTime());

			Criteria consulta = sessao.createCriteria(Sessao.class);
//			consulta.createAlias("aluno", "a");
//			consulta.add(Restrictions.eq("a.codigo", s.getAluno().getCodigo()));
//			
//			consulta.createAlias("sala", "s");
//			consulta.add(Restrictions.eq("s.codigo", s.getSala().getCodigo()));
			
			consulta.add(Restrictions.ge("dataInicio", s.getDataInicio()));
			consulta.add(Restrictions.le("dataFim",  s.getDataFim()));
			
			List<Sessao> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Sessao> listar(Date data, Date data2) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.add(Restrictions.between("dataInicio", data, data2));
			consulta.addOrder(Order.asc("dataInicio"));
			List<Sessao> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Number buscaNumeroSessoes(Frequencia frequencia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.add(Restrictions.eq("frequencia",frequencia));
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Number buscaNumeroFaltas(Long codigoPaciente, Frequencia frequencia) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.createAlias("paciente", "p");
			consulta.add(Restrictions.eq("p.codigo", codigoPaciente));
			consulta.add(Restrictions.eq("frequencia", frequencia));
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
}
