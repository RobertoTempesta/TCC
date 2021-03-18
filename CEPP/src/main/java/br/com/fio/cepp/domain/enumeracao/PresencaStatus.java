package br.com.fio.cepp.domain.enumeracao;

import lombok.Getter;
import lombok.Setter;

public enum PresencaStatus {

	P("Presente"), FJ("Falta Justificada"), FN("Falta N/ Justificada"), A("Agendado");
	
	@Getter @Setter
	private String descricao;
	
	private PresencaStatus(String descricao) {
		this.descricao = descricao;
	}
}
