package br.com.fio.cepp.domain.enumeracao;

import lombok.Getter;
import lombok.Setter;

public enum Sexo {

	M("Masculino"), F("Feminino");

	@Getter
	@Setter
	private String descricao;
	
	private Sexo(String descricao) {
		this.descricao = descricao;
	}
}
