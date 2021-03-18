CREATE TABLE formacao_academica (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  descricao varchar(150) NOT NULL,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO formacao_academica (descricao) values ('Fundamental - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Fundamental - Completo');
INSERT INTO formacao_academica (descricao) values ('Médio - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Médio - Completo');
INSERT INTO formacao_academica (descricao) values ('Superior - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Superior - Completo');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Lato senso) - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Lato senso) - Completo');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Stricto sensu, nível mestrado) - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Stricto sensu, nível mestrado) - Completo');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Stricto sensu, nível doutor) - Incompleto');
INSERT INTO formacao_academica (descricao) values ('Pós-graduação (Stricto sensu, nível doutor) - Completo');

ALTER TABLE pessoa
DROP COLUMN escolaridade,
DROP COLUMN sexo, 
ADD sexo varchar(1),
ADD formacao_academica_codigo bigint(20) NOT NULL,
ADD CONSTRAINT FK_pessoa_formacao_academica_codigo FOREIGN KEY (formacao_academica_codigo) REFERENCES formacao_academica (codigo);
