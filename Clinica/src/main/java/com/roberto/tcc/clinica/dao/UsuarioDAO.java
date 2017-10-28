package com.roberto.tcc.clinica.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.roberto.tcc.clinica.domain.Endereco;
import com.roberto.tcc.clinica.domain.Pessoa;
import com.roberto.tcc.clinica.domain.Usuario;
import com.roberto.tcc.clinica.security.Criptografia;
import com.roberto.tcc.clinica.util.HibernateUtil;

@SuppressWarnings("serial")
public class UsuarioDAO extends GenericDAO<Usuario> {

	public Usuario buscarCodigoPes(Long codigo) throws RuntimeException {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.codigo", codigo));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}

	}

	public Usuario autenticar(Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			String salt = getSaltCPF(usuario.getPessoa().getCPF());
			
			if(salt == null) {
				return null;
			}
			
			consulta.add(Restrictions.eq("senha", Criptografia.gerarSenhaCriptografada(usuario.getSenha() + salt)));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

	public String getSaltCPF(String CPF) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.CPF", CPF));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			if(resultado == null) {
				return null;
			}
			return resultado.getSalt();

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}
	
	public Usuario salvarPessoa(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			
			transacao = sessao.beginTransaction();
			usuario.getPessoa().setEndereco((Endereco) sessao.merge(usuario.getPessoa().getEndereco()));
			usuario.setPessoa((Pessoa) sessao.merge(usuario.getPessoa()));
			Usuario retorno = (Usuario) sessao.merge(usuario);
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

	public Usuario buscarUsuario(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {

			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");
			consulta.add(Restrictions.eq("p.CPF", usuario.getPessoa().getCPF()));
			consulta.add(Restrictions.eq("p.RG", usuario.getPessoa().getRG()));
			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;

		} catch (RuntimeException exception) {
			throw exception;
		} finally {
			sessao.close();
		}
	}

}
