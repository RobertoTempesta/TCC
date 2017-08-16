package com.roberto.tcc.clinica.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;

public class Constantes {

	public static final int ANO_CORRENTE = LocalDateTime.now().getYear();

	public static final String MASCULINO = "M";
	public static final String FEMININO = "F";
	
	public static final String CASADO = "C";
	public static final String SOLTEIRO = "S";
	public static final String DIVORCIADO = "D";
	public static final String VIUVO = "V";
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

}
