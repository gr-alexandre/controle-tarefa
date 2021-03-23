package com.ct.controletarefas.dtos;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TarefaDto {	
	private Long id;	
	private Long idUsuario;
	@NotEmpty(message = "Resumo da tarefa não pode ser vazio")
	@Length(min = 5, max = 255, message = "Resumo da tarefa deve conter entre 5 e 255 caracteres")
	private String resumoTarefa;
	@Length(min = 5, message = "Descrição da tarefa deve ter no mínimo 5 caracteres")
	@NotEmpty(message = "Descrição da tarefa não pode ser vazio")
	private String descricaoTarefa;
	private int status;
	private String statusDescricao;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date dataInclusao;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date dataAlteracao;
}
