package com.ct.controletarefas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import com.ct.controletarefas.enums.PerfilUsuarioEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
	private static final long serialVersionUID = -3009157732242241606L;	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nome", nullable=false)
	private String nome;

	@Column(name = "email", nullable=false)
	private String email;

	@Column(name = "password", nullable=false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil_usuario", nullable=false)
	private PerfilUsuarioEnum perfilUsuario;

	@Column(name = "fl_ativo", nullable=false)
	private Integer flAtivo;

	@Column(name = "data_inclusao", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;

}
