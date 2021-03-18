package br.com.fio.cepp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.fio.cepp.domain.enumeracao.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuario")
public class Usuario extends GenericDomain {
	
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cpf;

	@Column(nullable = false)
	private String senha;
	
	@Column(nullable = false)
	private Boolean ativo;
	
	@Column(name = "tipo_usuario")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;
	
}
