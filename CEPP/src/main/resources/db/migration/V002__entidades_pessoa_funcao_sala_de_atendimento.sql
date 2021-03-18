CREATE TABLE pessoa (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  cpf varchar(14) NOT NULL,
  rg varchar(12) NOT NULL,
  data_nascimento date DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  escolaridade varchar(100) NOT NULL,
  idade int(11) NOT NULL,
  nome varchar(50) NOT NULL,
  sexo char NOT NULL,
  endereco_cep varchar(10) NOT NULL,
  endereco_bairro varchar(50) NOT NULL,
  endereco_cidade varchar(50) NOT NULL,
  endereco_numero varchar(10) NOT NULL,
  endereco_rua varchar(50) NOT NULL,
  endereco_estado_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  UNIQUE KEY UK_cpf (cpf),
  CONSTRAINT FK_pessoa_endereco_estado_codigo FOREIGN KEY (endereco_estado_codigo) REFERENCES estado (codigo)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE telefone (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  numero varchar(13) NOT NULL,
  pessoa_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_telefone_pessoa_codigo FOREIGN KEY (pessoa_codigo) REFERENCES pessoa (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE sala_atendimento (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  descricao varchar(10) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE funcao (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  descricao varchar(50) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;