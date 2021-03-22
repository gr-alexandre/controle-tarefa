CREATE TABLE usuario (
  id int(11) NOT NULL AUTO_INCREMENT,
  nome varchar(255) NOT NULL,
  email varchar(50) NOT NULL,
  password varchar(255) NOT NULL,
  perfil_usuario varchar(255) NOT NULL,
  fl_ativo tinyint(4) NOT NULL,
  data_inclusao datetime DEFAULT NOW(),
  PRIMARY KEY (id)
)
ENGINE = INNODB DEFAULT CHARSET=utf8;

CREATE TABLE tarefa (
  id int(11) NOT NULL AUTO_INCREMENT,
  id_usuario int(11) NOT NULL,
  resumo_tarefa text NOT NULL,
  descricao_tarefa text NOT NULL,
  status tinyint(4) NOT NULL,
  data_inclusao datetime DEFAULT NOW(),
  data_alteracao datetime NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB DEFAULT CHARSET=utf8;