package br.com.fio.cepp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.util.HibernateUtil;

public class TelefoneDAO extends GenericDAO<Telefone> {

	private static final long serialVersionUID = 4588768421764299804L;
	
	@SuppressWarnings("unchecked")
	public List<Telefone> buscarPorPessoaCod(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Telefone.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			List<Telefone> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
