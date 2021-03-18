package br.com.fio.cepp.domain.enumeracao;

import lombok.Getter;
import lombok.Setter;

public enum Situacao {

	A ("Aguardando"), 
	AT("Sendo Atendido"),
	F("Finalizado");
	
	@Getter @Setter
	private String descricao;
	
	private Situacao(String descricao) {
		this.descricao = descricao;
	}
}
