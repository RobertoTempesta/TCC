package com.roberto.tcc.clinica.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.roberto.tcc.clinica.domain.Paciente;
import com.roberto.tcc.clinica.domain.Sessao;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class SessaoDAO extends GenericDAO<Sessao>{

	public Sessao merge(Sessao s) {
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
	
}
