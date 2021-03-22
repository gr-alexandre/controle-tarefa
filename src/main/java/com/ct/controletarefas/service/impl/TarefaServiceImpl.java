package com.ct.controletarefas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ct.controletarefas.entities.Tarefa;
import com.ct.controletarefas.repositories.TarefaRepository;
import com.ct.controletarefas.services.TarefaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TarefaServiceImpl implements TarefaService {
	@Autowired
	TarefaRepository tarefaRepository;	

	@CachePut("ListarPorID")
	public void salvarTarefa(Tarefa tarefa) {
		log.info("Persitir tarefa: {}", tarefa.toString());
		tarefaRepository.save(tarefa);		
	}

	public List<Tarefa> listarTarefasTodosUsuarios() {
		log.info("Listar tarefas de todos usuarios.");
		return tarefaRepository.findAllsByOrderByStatusAsc();
	}

	@Cacheable("ListarPorID")
	public List<Tarefa> listarTarefasPorIdUsuarios(Long idUsuario) {
		log.info("Listar tarefas do usuario: {}", idUsuario);
		return tarefaRepository.findByIdUsuarioOrderByStatusAsc(idUsuario);
	}
	
	
	public List<Tarefa> listarTarefasPorIdUsuariosPorStatus(Long idUsuario,int status) {
		log.info("Listar tarefas do usuario por idUsuario: {}", idUsuario);
		log.info("Listar tarefas do usuario por status: {}", status);
		return tarefaRepository.findByIdUsuarioAndStatusOrderByDataInclusaoDesc(idUsuario,status);
	}

	public void excluirTarefa(Long id) {
		log.info("Excluir tarefa: {}", id);
		tarefaRepository.deleteById(id);		
	}

}
