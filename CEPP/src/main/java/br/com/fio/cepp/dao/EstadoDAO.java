package br.com.fio.cepp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Estado;
import br.com.fio.cepp.util.HibernateUtil;

public class EstadoDAO extends GenericDAO<Estado> {

	private static final long serialVersionUID = 584845527183373611L;

	public Estado buscarPorSigla(String sigla) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Estado.class);
			consulta.add(Restrictions.ilike("sigla", sigla));
			Estado resultado = (Estado) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
