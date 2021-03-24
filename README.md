# controle-tarefa
Api do sistema de controle de tarefas Spring Boot

# Tecnologia
#Java 11
#Spring boot
#Junit 4
#flyway
#EhCache
#MariaDB
#swagger
#actuator
#prometheus
#lombok
#travisCi
#IDE eclipse
#maven
#H2

#Instalar lombok na IDE

#Para este exemplo não foi utilizado o token na sessão de um redis, etc... e sim sessão da aplicação

# LOG
#Alterar no arquivo(application.properties) do projeto o caminho onde o log será gravado esta como default logging.file.name=C:/logSpring/log_ControleTarefa.log

# Teste de integracao continua
#https://travis-ci.com/github/gr-alexandre/controle-tarefa

# Banco de dados

#Utilizei o Banco relacional mariaDB por nao precisar de tanta performace, poderia utilizar NOSQL

#Utilizei o  banco H2 para testes unitario

#Depois de instalar o banco de dados mariaDB criar o DataBase controle_tarefa

#Configuração do acesso ao base e drive de conexão application.properties (MariaDB)

#Configuração do acesso ao base e drive de conexão application-test.properties(H2)

#flyway que cria as tabelas da base de dados existe um script dentro do projeto  s\src\main\resources\db\migration\"V1__mariadb.sql"

#Criacão dos usuários default no DB controle_tarefa
#
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

#
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

# Documentação Api swagger
#http://localhost:8080/swagger-ui.html

# Teste da URL pelo PostMan

#Exemplo

#http://localhost:8080/api/controle-tarefa/auth
#
{
   "email":"gr@emai.com",
   "password":"123"
}


#Pegar no retorno o token e colocar no header para todos os enpoints abaixo

#Key = Authorization value = <token>

# Bustar tarefas  por idUser e status
#http://localhost:8080/api/controle-tarefa/tarefa/usuario/1/status/0

# Bustar tarefas por idUser
#http://localhost:8080/api/controle-tarefa/tarefa/usuario/1/

# Bustar tarefas de todos usuários - apenas ROLE_SUPER_USER
#http://localhost:8080/api/controle-tarefa/tarefa/

# Excluir tarefa 
#http://localhost:8080/api/controle-tarefa/tarefa/id/8/usuario/4

# Incluir tarefa
#http://localhost:8080/api/controle-tarefa/tarefa
#
{
   "idUsuario":2,
   "resumoTarefa":"Teste",
   "descricaoTarefa":"TESTE  TESTE"
}

# Alterar tarefa 
#http://localhost:8080/api/controle-tarefa/tarefa
#
{
    "id": 9,
    "idUsuario":2,
    "resumoTarefa":"Teste update",
    "descricaoTarefa":"TESTE  TESTE ----"
}

# Monitoramento
#http://localhost:8080/actuator/
#http://localhost:8080/actuator/healthcheck
#http://localhost:8080/actuator/prometheus
#http://localhost:8080/actuator/metrics/
