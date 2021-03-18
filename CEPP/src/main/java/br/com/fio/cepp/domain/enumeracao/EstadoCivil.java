package br.com.fio.cepp.domain.enumeracao;

import lombok.Getter;
import lombok.Setter;

public enum EstadoCivil {

	S("Solteiro(a)"), C("Casado(a)"), V("Viúvo(a)"), D("Divorciado(a)"), A("Amigado"), O("Outro");
	
	@Getter @Setter
	private String descricao;
	
	private EstadoCivil(String descricao) {
		this.descricao = descricao;
	}
	
}
