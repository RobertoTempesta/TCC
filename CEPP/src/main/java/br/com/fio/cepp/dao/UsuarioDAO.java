package br.com.fio.cepp.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Usuario;
import br.com.fio.cepp.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	public Usuario buscarPorCpf(String cpf) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.add(Restrictions.eq("cpf", cpf));
			return (Usuario) consulta.uniqueResult();

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
