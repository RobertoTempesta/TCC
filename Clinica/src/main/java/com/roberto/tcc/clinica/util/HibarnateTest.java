package com.roberto.tcc.clinica.util;

import org.hibernate.Session;
import org.junit.Ignore;
import org.junit.Test;

import com.roberto.tcc.clinica.dao.SessaoDAO;
import com.roberto.tcc.clinica.enumeracao.Frequencia;

public class HibarnateTest {
	
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
	
	@Test
	@Ignore
	public void numero(){
		SessaoDAO dao = new SessaoDAO();
		System.out.println("Faltas "+dao.buscaNumeroFaltas(3L, Frequencia.FALTA_INJUSTIFICADA));
	}

}
