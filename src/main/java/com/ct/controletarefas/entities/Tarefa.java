package com.ct.controletarefas.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tarefa")
public class Tarefa implements Serializable {
	
	private static final long serialVersionUID = -7526502149208345058L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "id_usuario", nullable=false)
	private Long idUsuario;	
	
	@Column(name = "resumo_tarefa", nullable=false)
	private String resumoTarefa;
	
	@Column(name = "descricao_tarefa", nullable=false)
	private String descricaoTarefa;
	
	@Column(name = "status", nullable=false)
	private int status;
	
	@Column(name = "data_inclusao", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss")
    private Date dataInclusao;
	
	@Column(name = "data_alteracao", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	//@JsonFormat(pattern="yyyy-MM-ddTHH:mm:ss")
    private Date dataAlteracao;
	
	
}
