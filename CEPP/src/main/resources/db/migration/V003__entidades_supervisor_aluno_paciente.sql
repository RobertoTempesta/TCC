CREATE TABLE supervisor (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  crp varchar(15) NOT NULL,
  data_cadastro date NOT NULL,
  pessoa_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_supervisor_pessoa_codigo FOREIGN KEY (pessoa_codigo) REFERENCES pessoa (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE aluno (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  ra varchar(13) NOT NULL,
  data_cadastro date NOT NULL,
  funcao_codigo bigint(20) NOT NULL,
  pessoa_codigo bigint(20) NOT NULL,
  supervisor_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_aluno_supervisor_codigo FOREIGN KEY (supervisor_codigo) REFERENCES supervisor (codigo),
  CONSTRAINT FK_aluno_pessoa_codigo FOREIGN KEY (pessoa_codigo) REFERENCES pessoa (codigo),
  CONSTRAINT FK_aluno_funcao_codigo FOREIGN KEY (funcao_codigo) REFERENCES funcao (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE paciente (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  data_cadastro date NOT NULL,
  estado_civil varchar(255) NOT NULL,
  faltas_injustificadas int(11) DEFAULT NULL,
  faltas_justificadas int(11) DEFAULT NULL,
  medicamento varchar(100) DEFAULT NULL,
  necessidades_especiais varchar(100) DEFAULT NULL,
  nome_mae varchar(80) NOT NULL,
  nome_pai varchar(80) NOT NULL,
  numero_caso varchar(50) DEFAULT NULL,
  observacao varchar(500) DEFAULT NULL,
  ocupacao varchar(100) NOT NULL,
  pessoa_autorizada varchar(80) DEFAULT NULL,
  presencas int(11) DEFAULT NULL,
  responsavel_celular varchar(13) DEFAULT NULL,
  responsavel_nome varchar(80) NOT NULL,
  responsavel_telefone varchar(13) DEFAULT NULL,
  situacao varchar(255) NOT NULL,
  pessoa_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_paciente_pessoa_codigo FOREIGN KEY (pessoa_codigo) REFERENCES pessoa (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;