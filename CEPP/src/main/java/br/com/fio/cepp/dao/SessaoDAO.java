package br.com.fio.cepp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Sessao;
import br.com.fio.cepp.domain.enumeracao.PresencaStatus;
import br.com.fio.cepp.domain.enumeracao.Situacao;
import br.com.fio.cepp.util.HibernateUtil;

public class SessaoDAO extends GenericDAO<Sessao> {

	private static final long serialVersionUID = 5633417411575690899L;
	
	public Sessao merge(Sessao s) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			if (s.getPaciente().getSituacao().equals(Situacao.A)) {
				Paciente paciente = s.getPaciente();
				paciente.setSituacao(Situacao.AT);
				paciente = (Paciente) sessao.merge(paciente);
				s.setPaciente(paciente);
			}
			
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
	public List<Sessao> verificarAgendamentoLivre(Sessao s) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			
			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.createAlias("aluno", "a");
			consulta.createAlias("salaAtendimento", "s");
			
			consulta.add(Restrictions.disjunction()
					.add(Restrictions.eq("a.codigo", s.getAluno().getCodigo()))
					.add(Restrictions.eq("s.codigo", s.getSalaAtendimento().getCodigo())));
			
			consulta.add(Restrictions.between("dataInicio", s.getDataInicio(), s.getDataFim()));
			consulta.add(Restrictions.between("dataFim", s.getDataInicio(), s.getDataFim()));
			
			List<Sessao> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Number getNumeroSessoesAtendidas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.add(Restrictions.eq("presencaStatus", PresencaStatus.P));
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Number getPresencaStatus(PresencaStatus status, Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.add(Restrictions.eq("presencaStatus", status));
			consulta.createAlias("paciente", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Sessao> listar(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Sessao.class);
			consulta.createAlias("paciente", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			consulta.addOrder(Order.desc("codigo"));
			return consulta.list();
		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
