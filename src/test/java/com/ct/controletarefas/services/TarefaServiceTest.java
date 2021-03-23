package com.ct.controletarefas.services;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ct.controletarefas.entities.Tarefa;
import com.ct.controletarefas.repositories.TarefaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TarefaServiceTest {

	@MockBean
	TarefaRepository tarefaRepository;

	@Autowired
	TarefaService tarefaService;


	@Test
	public void testListarTarefasTodosUsuarios(){	

		BDDMockito.given(this.tarefaRepository.findAllsByOrderByStatusAsc()).willReturn(preencherListaTarefa());
		List<Tarefa> listaTarefa = this.tarefaService.listarTarefasTodosUsuarios();
		assertNotNull(listaTarefa);
	}

	public void testListarTarefasPorIdUsuarios() {
		BDDMockito.given(this.tarefaRepository.findByIdUsuarioOrderByStatusAsc(Mockito.anyLong())).willReturn(preencherListaTarefa());
		List<Tarefa> listaTarefa = this.tarefaService.listarTarefasPorIdUsuarios((long) 1);
		assertNotNull(listaTarefa);
	}	

	public void testListarTarefasPorIdUsuariosPorStatus() {
		BDDMockito.given(this.tarefaRepository.findByIdUsuarioAndStatusOrderByDataInclusaoDesc(Mockito.anyLong(),Mockito.anyInt())).willReturn(preencherListaTarefa());
		List<Tarefa> listaTarefa = this.tarefaService.listarTarefasPorIdUsuariosPorStatus((long) 1, 0);
		assertNotNull(listaTarefa);		
	}	

	private List<Tarefa> preencherListaTarefa(){

		ArrayList<Tarefa> listaTarefa = new ArrayList<>();
		Tarefa tarefa = new Tarefa();
		tarefa.setId((long) 1);
		tarefa.setIdUsuario((long) 1);
		tarefa.setResumoTarefa("Teste RESUMO - 01");
		tarefa.setDescricaoTarefa("Teste Descricao - 01");
		tarefa.setStatus(0);
		tarefa.setDataInclusao(new Date());
		listaTarefa.add(tarefa);
		return listaTarefa;
	}
}
