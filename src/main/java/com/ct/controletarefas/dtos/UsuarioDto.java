package com.ct.controletarefas.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ct.controletarefas.enums.PerfilUsuarioEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioDto {
	private Long id;
	private String nome;
	private String email;
	private PerfilUsuarioEnum perfilUsuario;
	private Integer flAtivo;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInclusao;
	private String token;
}
