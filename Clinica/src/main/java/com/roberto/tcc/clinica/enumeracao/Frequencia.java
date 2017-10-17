package com.roberto.tcc.clinica.enumeracao;

public enum Frequencia {

	PRESENTE, FALTA_JUSTIFICADA, FALTA_INJUSTIFICADA, AGUARDANDO;

	@Override
	public String toString() {
		switch (this) {

		case PRESENTE:
			return "Presente";

		case FALTA_JUSTIFICADA:
			return "Falta Justificada";

		case FALTA_INJUSTIFICADA:
			return "Falta não Justificada";
			
		case AGUARDANDO:
			return "Aguardando";
			
		default:
			return null;

		}
	}
}
