CREATE TABLE sessao (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  data_fim datetime NOT NULL,
  data_inicio datetime NOT NULL,
  presenca_status varchar(2) NOT NULL,
  observacao varchar(500) DEFAULT NULL,
  aluno_codigo bigint(20) NOT NULL,
  paciente_codigo bigint(20) NOT NULL,
  sala_atendimento_codigo bigint(20) NOT NULL,
  PRIMARY KEY (codigo),
  CONSTRAINT FK_sessao_paciente_codigo FOREIGN KEY (paciente_codigo) REFERENCES paciente (codigo),
  CONSTRAINT FK_sessao_aluno_codigo FOREIGN KEY (aluno_codigo) REFERENCES aluno (codigo),
  CONSTRAINT FK_sessao_sala_atendimento_codigo FOREIGN KEY (sala_atendimento_codigo) REFERENCES sala_atendimento (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
