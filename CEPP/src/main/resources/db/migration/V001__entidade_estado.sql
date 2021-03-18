CREATE TABLE estado (
  codigo bigint(20) NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  sigla varchar(2) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

INSERT INTO estado (sigla, nome) values ('AC', 'Acre');
INSERT INTO estado (sigla, nome) values ('AL', 'Alagoas');
INSERT INTO estado (sigla, nome) values ('AM', 'Amazonas');
INSERT INTO estado (sigla, nome) values ('AP', 'Amapá');
INSERT INTO estado (sigla, nome) values ('BA', 'Bahia');
INSERT INTO estado (sigla, nome) values ('CE', 'Ceará');
INSERT INTO estado (sigla, nome) values ('DF', 'Distrito Federal');
INSERT INTO estado (sigla, nome) values ('ES', 'Espírito Santo');
INSERT INTO estado (sigla, nome) values ('GO', 'Goiás');
INSERT INTO estado (sigla, nome) values ('MA', 'Maranhão');
INSERT INTO estado (sigla, nome) values ('MG', 'Minas Gerais');
INSERT INTO estado (sigla, nome) values ('MS', 'Mato Grosso do Sul');
INSERT INTO estado (sigla, nome) values ('MT', 'Mato Grosso');
INSERT INTO estado (sigla, nome) values ('PA', 'Pará');
INSERT INTO estado (sigla, nome) values ('PB', 'Paraíba');
INSERT INTO estado (sigla, nome) values ('PE', 'Pernambuco');
INSERT INTO estado (sigla, nome) values ('PI', 'Piauá');
INSERT INTO estado (sigla, nome) values ('PR', 'Paraná');
INSERT INTO estado (sigla, nome) values ('RJ', 'Rio de Janeiro');
INSERT INTO estado (sigla, nome) values ('RN', 'Rio Grande do Norte');
INSERT INTO estado (sigla, nome) values ('RO', 'Rondônia');
INSERT INTO estado (sigla, nome) values ('RR', 'Roraima');
INSERT INTO estado (sigla, nome) values ('RS', 'Rio Grande do Sul');
INSERT INTO estado (sigla, nome) values ('SC', 'Santa Catarina');
INSERT INTO estado (sigla, nome) values ('SE', 'Sergipe');
INSERT INTO estado (sigla, nome) values ('SP', 'São Paulo');
INSERT INTO estado (sigla, nome) values ('TO', 'Tocantins');
