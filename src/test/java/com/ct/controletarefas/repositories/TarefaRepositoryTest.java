package com.ct.controletarefas.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ct.controletarefas.entities.Tarefa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TarefaRepositoryTest {
	
	@Autowired
	TarefaRepository tarefaRepository;
	

	@Test
	public void testFindAllsByOrderByStatusAsc() {
		List<Tarefa> listaTarefas = preencherDadosTarefa();
		List<Tarefa> listaTarefasRetorno;
		
		for (Tarefa tarefa : listaTarefas) {
			tarefaRepository.save(tarefa);
		}		
		listaTarefasRetorno = tarefaRepository.findAllsByOrderByStatusAsc();		
		assertEquals(3, listaTarefasRetorno.size());
		this.tarefaRepository.deleteAll();
	}
	
	@Test
	public void testFindByIdUsuarioOrderByStatusAsc(){
		
		List<Tarefa> listaTarefas = preencherDadosTarefa();
		List<Tarefa> listaTarefasRetorno;
		
		for (Tarefa tarefa : listaTarefas) {
			tarefaRepository.save(tarefa);
		}	
		
		listaTarefasRetorno = tarefaRepository.findByIdUsuarioOrderByStatusAsc((long) 1);		
		assertEquals(2, listaTarefasRetorno.size());
		
		listaTarefasRetorno = tarefaRepository.findByIdUsuarioOrderByStatusAsc((long) 2);		
		assertEquals(1, listaTarefasRetorno.size());
		this.tarefaRepository.deleteAll();
		
	}
	
	
	@Test
	public void testFindByIdUsuarioAndStatusOrderByDataInclusaoDesc() {
		
		List<Tarefa> listaTarefas = preencherDadosTarefa();
		List<Tarefa> listaTarefasRetorno;
		
		for (Tarefa tarefa : listaTarefas) {
			tarefaRepository.save(tarefa);
		}	
		
		listaTarefasRetorno = tarefaRepository.findByIdUsuarioAndStatusOrderByDataInclusaoDesc((long) 1,0);		
		assertEquals(1, listaTarefasRetorno.size());
		
		listaTarefasRetorno = tarefaRepository.findByIdUsuarioAndStatusOrderByDataInclusaoDesc((long) 1,1);		
		assertEquals(1, listaTarefasRetorno.size());
		
		listaTarefasRetorno = tarefaRepository.findByIdUsuarioAndStatusOrderByDataInclusaoDesc((long) 2,0);		
		assertEquals(1, listaTarefasRetorno.size());
		this.tarefaRepository.deleteAll();
		
	}
	
	private ArrayList<Tarefa> preencherDadosTarefa() {
		ArrayList<Tarefa> listaTarefa = new ArrayList<>();
		Tarefa tarefa = new Tarefa();
		tarefa.setId((long) 1);
		tarefa.setIdUsuario((long) 1);
		tarefa.setResumoTarefa("Teste RESUMO - 01");
		tarefa.setDescricaoTarefa("Teste Descricao - 01");
		tarefa.setStatus(0);
		tarefa.setDataInclusao(new Date());
		listaTarefa.add(tarefa);
		

		tarefa = new Tarefa();
		tarefa.setId((long) 2);
		tarefa.setIdUsuario((long) 1);
		tarefa.setResumoTarefa("Teste RESUMO - 02");
		tarefa.setDescricaoTarefa("Teste Descricao - 02");
		tarefa.setStatus(1);
		tarefa.setDataInclusao(new Date());
		tarefa.setDataAlteracao(new Date());
		listaTarefa.add(tarefa);
		
		tarefa = new Tarefa();
		tarefa.setId((long) 3);
		tarefa.setIdUsuario((long) 2);
		tarefa.setResumoTarefa("Teste RESUMO - 03");
		tarefa.setDescricaoTarefa("Teste Descricao - 03");
		tarefa.setStatus(0);
		tarefa.setDataInclusao(new Date());
		listaTarefa.add(tarefa);		
		return listaTarefa;
	}

}
