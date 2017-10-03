package com.roberto.tcc.clinica.util;

import org.hibernate.Session;
import org.junit.Test;

public class HibarnateTest {
	
	@Test
	public void conectar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}

}
