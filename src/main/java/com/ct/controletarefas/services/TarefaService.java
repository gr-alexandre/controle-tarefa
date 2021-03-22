package com.ct.controletarefas.services;

import java.util.List;

import com.ct.controletarefas.entities.Tarefa;

public interface TarefaService {
	
	public void salvarTarefa(Tarefa tarefa);
	
	public List<Tarefa> listarTarefasTodosUsuarios();
	
	public List<Tarefa> listarTarefasPorIdUsuarios(Long idUsuario);
	
	List<Tarefa> listarTarefasPorIdUsuariosPorStatus(Long idUsuario,int status);
	
	public void excluirTarefa(Long id);
	
}
