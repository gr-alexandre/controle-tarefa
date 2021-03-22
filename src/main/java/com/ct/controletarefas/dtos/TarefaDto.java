package com.ct.controletarefas.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TarefaDto {	
	private Long id;	
	private Long idUsuario;	
	private String resumoTarefa;
	private String descricaoTarefa;
	private int status;
	private String statusDescricao;
    private Date dataInclusao;
    private Date dataAlteracao;
}
