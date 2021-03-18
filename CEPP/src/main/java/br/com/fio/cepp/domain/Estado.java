package br.com.fio.cepp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estado")
public class Estado extends GenericDomain{

	private static final long serialVersionUID = 6072630175989569320L;

	@Column(length = 2, nullable = false)
	private String sigla;
	
	@Column(length = 50, nullable = false)
	private String nome;
	
}
