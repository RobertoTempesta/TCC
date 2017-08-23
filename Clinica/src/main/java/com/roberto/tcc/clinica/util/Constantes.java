package com.roberto.tcc.clinica.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;

public class Constantes {

	public static final int ANO_CORRENTE = LocalDateTime.now().getYear();

	public static final char MASCULINO = 'M';
	public static final char FEMININO = 'F';
	
	public static final char CASADO = 'C';
	public static final char SOLTEIRO = 'S';
	public static final char DIVORCIADO = 'D';
	public static final char VIUVO = 'V';
	
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));

}
