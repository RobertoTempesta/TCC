CREATE TABLE usuario (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(100) DEFAULT NULL,
  cpf varchar(13) DEFAULT NULL,
  senha varchar(200) DEFAULT NULL,
  tipo_usuario varchar(10) NOT NULL, 
  ativo bit(1) DEFAULT TRUE,
  PRIMARY KEY (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
