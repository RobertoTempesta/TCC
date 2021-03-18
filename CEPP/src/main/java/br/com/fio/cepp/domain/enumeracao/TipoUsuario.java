package br.com.fio.cepp.domain.enumeracao;

import lombok.Getter;
import lombok.Setter;

public enum TipoUsuario {

	ADMIN("Administrador"), 
	COMUM("Comum");
	
	@Getter @Setter
	private String descricao;
	
	private TipoUsuario(String descricao) {
		this.descricao = descricao;
	}
}
