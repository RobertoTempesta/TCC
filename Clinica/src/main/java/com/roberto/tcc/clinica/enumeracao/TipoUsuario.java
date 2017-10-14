package com.roberto.tcc.clinica.enumeracao;

public enum TipoUsuario {

	GERENCIADOR, USUARIO;

	@Override
	public String toString() {
		switch (this) {

		case GERENCIADOR:
			return "Gerênciador";

		case USUARIO:
			return "Usuário";

		default:
			return null;
		}

	}

}
