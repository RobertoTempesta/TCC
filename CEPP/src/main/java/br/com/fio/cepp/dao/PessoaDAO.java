package br.com.fio.cepp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.util.HibernateUtil;

public class PessoaDAO extends GenericDAO<Pessoa> {

	private static final long serialVersionUID = 1881222772703143187L;

	
	public Pessoa buscarPorCPF(String cpf) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Pessoa.class);
			consulta.add(Restrictions.ilike("cpf", cpf));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}
}
