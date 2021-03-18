package br.com.fio.cepp.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Supervisor;
import br.com.fio.cepp.util.HibernateUtil;

public class SupervisorDAO extends GenericDAO<Supervisor>  {

	private static final long serialVersionUID = 2965068535650612268L;
	
	public Supervisor merge(Supervisor supervisor) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			Pessoa pessoa = supervisor.getPessoa();
			//validaPessoa(pessoa);
			pessoa = (Pessoa) sessao.merge(pessoa);
			supervisor.setPessoa(pessoa);
			
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
