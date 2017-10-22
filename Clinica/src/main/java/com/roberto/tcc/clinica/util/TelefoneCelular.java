package com.roberto.tcc.clinica.util;

public class TelefoneCelular {

	public boolean validarTelefone(String telefone) {

		// String formato = "\([0-9]{2}?\)[0-9]{4}?\-[0-9]{4}?";
		String formato = ".((10)|([1-9][1-9]).)[2-5][0-9]{3}-[0-9]{4}";
		// \((10)|([1-9][1-9])\) [2-9][0-9]{3}-[0-9]{4}

		if ((telefone == null) || (telefone.length() != 13) || (!telefone.matches(formato))) {
			return false;
		} else {
			return true;
		}

	}

	public boolean validarCelular(String cel) {

		// String formato = ".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}";
		String formato = ".([0-9]{2}?.)[0-9]{4}?.-[0-9]{4}?";

		if ((cel == null) || (cel.length() != 14) || (!cel.matches(formato))) {
			return false;
		} else {
			return true;
		}
	}

}
