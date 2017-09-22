package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class UsuarioDAO extends GenericDAO<Usuario>{
	
	public Usuario buscarCodigoPes(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Usuario resultado =  (Usuario) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}

}
