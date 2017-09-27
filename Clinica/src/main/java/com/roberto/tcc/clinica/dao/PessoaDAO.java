package com.roberto.tcc.clinica.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Cidade;
import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class PessoaDAO extends GenericDAO<Pessoa> {

	public Pessoa buscarCPF(String cpf) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Pessoa.class);
			consulta.add(Restrictions.ilike("CPF", cpf));
			Pessoa resultado = (Pessoa) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}
	
	public Pessoa salvarCustomizado(Pessoa pessoa) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			
			Cidade cidade = (Cidade) sessao.merge(pessoa.getEndereco().getCidade());
			Endereco endereco = new Endereco();
			endereco = pessoa.getEndereco();
			endereco.setCidade(cidade);
			endereco = (Endereco) sessao.merge(endereco);
			pessoa.setEndereco(endereco);
			Pessoa resultado = (Pessoa) sessao.merge(pessoa);
			
			transacao.commit();
			return resultado;
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
