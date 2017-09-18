package com.roberto.tcc.clinica.test.cep;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;

import com.roberto.tcc.clinica.util.CEPUtil;



public class BuscaCEPTest {

	@Test
	public void busca(){
		

		CEPUtil u = new CEPUtil();
		try {
			JSONObject o = u.capturaJson("0");
			System.out.println(o.get("localidade"));
		} catch (IOException e) {
			if(e.equals(400)) {
				System.out.println(true);
			}
			e.printStackTrace();
		}
		
		
	}
	
}
