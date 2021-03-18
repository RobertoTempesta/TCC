package br.com.fio.cepp.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.fio.cepp.domain.Paciente;
import br.com.fio.cepp.domain.Pessoa;
import br.com.fio.cepp.domain.Telefone;
import br.com.fio.cepp.domain.enumeracao.Situacao;
import br.com.fio.cepp.util.HibernateUtil;

public class PacienteDAO extends GenericDAO<Paciente> {

	private static final long serialVersionUID = 5722510647875830147L;

	public Paciente merge(Paciente paciente) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		List<Telefone> telefones = new ArrayList<>();
		List<Telefone> telefonesSalvos = new ArrayList<>();

		try {
			telefones = paciente.getPessoa().getTelefones();
			paciente.getPessoa().setTelefones(new ArrayList<>());

			transacao = sessao.beginTransaction();

			Pessoa pessoa = (Pessoa) sessao.merge(paciente.getPessoa());

			for (Telefone tel : telefones) {
				tel.setPessoa(pessoa);
				tel = (Telefone) sessao.merge(tel);
				telefonesSalvos.add(tel);
			}

			pessoa.setTelefones(telefonesSalvos);
			paciente.setPessoa(pessoa);

			Paciente pacienteSalvo = (Paciente) sessao.merge(paciente);

			StringBuilder numeroCaso = new StringBuilder();
			numeroCaso.append(pacienteSalvo.getCodigo());
			numeroCaso.append("/");
			numeroCaso.append(LocalDateTime.now().getYear());
			pacienteSalvo.setNumeroCaso(numeroCaso.toString());

			pacienteSalvo = (Paciente) sessao.merge(pacienteSalvo);

			transacao.commit();
			return pacienteSalvo;
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public Number getNumeroPacientes() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Paciente.class);
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

	public Number getNumeroPacientesAguardando() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Number resultado = 0;
		try {

			Criteria consulta = sessao.createCriteria(Paciente.class);
			consulta.add(Restrictions.eq("situacao", Situacao.A));
			resultado = (Number) consulta.setProjection(Projections.rowCount()).uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Paciente> listar(boolean aguardando) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Paciente.class);
			if (aguardando) {
				consulta.addOrder(Order.asc("dataCadastro"));
				consulta.add(Restrictions.eq("situacao", Situacao.A));
			} else {
				consulta.add(Restrictions.ne("situacao", Situacao.F));
			}
			return consulta.list();

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
}
