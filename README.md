# controle-tarefa
Api do sistema de controle de tarefas Spring Boot

#Tecnologia
Java 11
Spring boot
Junit 4
flyway
EhCache
MariaDB
swagger
actuator
prometheus
lombok
travisCi


#Teste de integracao continua
https://travis-ci.com/github/gr-alexandre/controle-tarefa

#Para este exemplo não colocado o token na sessão de um redis, etc...

#Instalar lombok na IDE

# Alterar no arquivo do projeto o caminho onde o log irá gravar application.properties esta default logging.file.name=C:/logSpring/log_ControleTarefa.log

# Utilizei o Banco relacional mariaDB por nao precisar de tanta performace, poderia utilizar NOSQL

#Criacao usuario default
Criar DB controle_tarefa

#flyway que cria as tabelas da base de dados existe um script dentro do projeto  s\src\main\resources\db\migration\"V1__mariadb.sql"

#Criacao usuarios default no DB controle_tarefa


INSERT INTO usuario
(
nome
 ,email
 ,password
 ,perfil_usuario
 ,fl_ativo
 ,data_inclusao
)
VALUES
(
 
 'USUARIO SUPER' -- nome - VARCHAR(255) NOT NULL
 ,'email.super@email.com' -- email - VARCHAR(50) NOT NULL
 ,'12345' -- password - VARCHAR(255) NOT NULL
 ,'ROLE_SUPER_USER' -- perfil_usuario - VARCHAR(255) NOT NULL
 ,0 -- fl_ativo - TINYINT(4) NOT NULL
 ,NOW() -- data_inclusao - DATETIME
);


INSERT INTO usuario
(
nome
 ,email
 ,password
 ,perfil_usuario
 ,fl_ativo
 ,data_inclusao
)
VALUES
(
 
 'USUARIO USER' -- nome - VARCHAR(255) NOT NULL
 ,'email.user@email.com' -- email - VARCHAR(50) NOT NULL
 ,'12345' -- password - VARCHAR(255) NOT NULL
 ,'ROLE_USER' -- perfil_usuario - VARCHAR(255) NOT NULL
 ,0 -- fl_ativo - TINYINT(4) NOT NULL
 ,NOW() -- data_inclusao - DATETIME
);

#Documentação Api swagger
http://localhost:8080/swagger-ui.html

# Teste da URL pelo PostMan

Exemplo

http://localhost:8080/api/controle-tarefa/auth
{
   "email":"gr@emai.com",
   "password":"123"
}


#Pegar no retorno o token e colocar no header para todas as enpoints abaixo
Key = Authorization value = eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnckBlbWFpLmNvbSIsImV4cCI6MTYxNjQzNzA3MH0.EPbkowpS2DvAGmljiZMRlm6iElgDZQmVtXfTknGAvaaiWFHpI3No26VwUdkeKUMUXCyMbldE1QrmH25_aIg7tA

# Bustar tarefas  por idUser e status
http://localhost:8080/api/controle-tarefa/tarefa/usuario/1/status/0

# Bustar tarefas por idUser
http://localhost:8080/api/controle-tarefa/tarefa/usuario/1/

# Bustar tarefas de todos Usuarios - Apenas ROLE_SUPER_USER
http://localhost:8080/api/controle-tarefa/tarefa/

#Excluir tarefa 
http://localhost:8080/api/controle-tarefa/tarefa/id/8/usuario/4

#Incluir tarefa
http://localhost:8080/api/controle-tarefa/tarefa
{
   "idUsuario":2,
   "resumoTarefa":"Teste",
   "descricaoTarefa":"TESTE  TESTE"
}

#Alterar tarefa 
http://localhost:8080/api/controle-tarefa/tarefa

{
    "id": 9,
    "idUsuario":2,
    "resumoTarefa":"Teste update",
    "descricaoTarefa":"TESTE  TESTE ----"
}

#Monitoramento
http://localhost:8080/actuator/
http://localhost:8080/actuator/healthcheck
http://localhost:8080/actuator/prometheus
http://localhost:8080/actuator/metrics/
