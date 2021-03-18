# Comandos úteis:

1 - Para subir os containers basta executar na raiz do projeto os comandos:
$ docker-compose build --no-cache 
$ docker-compose up -d

2 - Para recompilar após efetuar alguma alteração no código Java basta executar apenas o container específico do maven. Ele fará o papel de recompilar o código e copiar os artefatos para o volume compartilhado entre os dois containers.
$ docker-compose run maven