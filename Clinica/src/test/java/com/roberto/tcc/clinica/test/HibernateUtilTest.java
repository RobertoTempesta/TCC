package com.roberto.tcc.clinica.test;

import org.hibernate.Session;
import org.junit.Test;

import com.roberto.tcc.clinica.util.HibernateUtil;

public class HibernateUtilTest {

	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
}
