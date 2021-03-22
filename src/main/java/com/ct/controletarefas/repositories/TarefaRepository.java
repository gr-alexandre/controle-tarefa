package com.ct.controletarefas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ct.controletarefas.entities.Tarefa;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
	
	@Transactional(readOnly = true)
	List<Tarefa> findAllsByOrderByStatusAsc();
	
	@Transactional(readOnly = true)
	List<Tarefa> findByIdUsuarioOrderByStatusAsc(Long idUsuario);
	
	@Transactional(readOnly = true)
	List<Tarefa> findByIdUsuarioAndStatusOrderByDataInclusaoDesc(Long idUsuario,int status);
}
