package com.ct.controletarefas.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.controletarefas.dtos.TarefaDto;
import com.ct.controletarefas.dtos.UsuarioDto;
import com.ct.controletarefas.entities.Tarefa;
import com.ct.controletarefas.enums.PerfilUsuarioEnum;
import com.ct.controletarefas.enums.StatusTarefaEnum;
import com.ct.controletarefas.responses.Response;
import com.ct.controletarefas.services.TarefaService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/controle-tarefa/tarefa")
@CrossOrigin(origins = "*")
public class TarefaController {
	
	@Autowired
	TarefaService tarefaService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> incluir(@Valid @RequestBody TarefaDto tarefaDto,HttpServletRequest req, HttpServletResponse res, BindingResult result, HttpSession session) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
			
		if(usuarioLogado==null||usuarioLogado.getId()!=tarefaDto.getIdUsuario()) {
			log.error("Erro Incluir Tarefa - nao autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		tarefaDto.setIdUsuario(usuarioLogado.getId());
		
		Tarefa tarefa = this.converterParaTarefa(tarefaDto);
		
		Response<TarefaDto> response = new Response<TarefaDto>();
		if(result==null) {
			log.error("Erro Incluir Tarefa; {}" + result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro Incluir Tarefa");
		}
		
		tarefaService.salvarTarefa(tarefa);
		
		return ResponseEntity.ok("Tarefa incluida com sucesso");
	}
	
	@GetMapping(consumes = "application/json")
	public ResponseEntity<ArrayList<TarefaDto>> listarTodasTarefasTodosUsuarios(HttpServletRequest req, HttpServletResponse res, HttpSession session ) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
		
		if(usuarioLogado==null||!usuarioLogado.getPerfilUsuario().toString().equals(PerfilUsuarioEnum.ROLE_SUPER_USER.toString())) {
			log.error("Erro Listar tarefas de todos Usuarios - nao autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
			
		List<Tarefa> listaTarefa = tarefaService.listarTarefasTodosUsuarios();
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		return ResponseEntity.ok(listaTarefaDto);
		
	}
	
	@GetMapping(value="/usuario/{idUsuario}" )
	public ResponseEntity<ArrayList<TarefaDto>> listarTodasTarefasDoUsuario(@PathVariable Long idUsuario,HttpServletRequest req, HttpServletResponse res,HttpSession session) throws Exception {
		
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
		
		if(usuarioLogado==null||usuarioLogado.getId()!=idUsuario) {
			log.error("Erro Listar tarefas do Usuario - nao autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Tarefa> listaTarefa = tarefaService.listarTarefasPorIdUsuarios(usuarioLogado.getId());
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		return ResponseEntity.ok(listaTarefaDto);
		
	}
	
	@GetMapping(value="/usuario/{idUsuario}/status/{status}" )
	public ResponseEntity<ArrayList<TarefaDto>> listarTodasTarefasDoUsuarioPorStatus(@PathVariable Long idUsuario,@PathVariable int status,HttpSession session) throws Exception {
		
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
		
		if(usuarioLogado==null||usuarioLogado.getId()!=idUsuario) {
			log.error("Erro Listar tarefas do Usuario - nao autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		List<Tarefa> listaTarefa = tarefaService.listarTarefasPorIdUsuariosPorStatus(usuarioLogado.getId(),status);
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		return ResponseEntity.ok(listaTarefaDto);
		
	}
	
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<String> update(@Valid @RequestBody TarefaDto tarefaDto,HttpServletRequest req, HttpServletResponse res, BindingResult result, HttpSession session ) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
		Tarefa tarefa = this.converterParaTarefa(tarefaDto);		
		
		Response<TarefaDto> response = new Response<TarefaDto>();
		if(usuarioLogado==null||usuarioLogado.getId()!=tarefaDto.getIdUsuario()) {
			log.error("Erro ao Alterar Tarefa - nao autorizado" );
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro Ao alterar Tarefa - nao autorizado");
		}
		
		if(result==null) {
			log.error("Erro ao Alterar Tarefa; {}" + result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro Ao alterar Tarefa");
		}		
		tarefaService.salvarTarefa(tarefa);
		
		return ResponseEntity.ok("Tarefa alterada com sucesso");
	}
	
	@DeleteMapping(value = "/id/{id}/usuario/{idUsuario}")
	public ResponseEntity<String> exluir(@PathVariable Long id,@PathVariable Long idUsuario, HttpSession session) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
			
		if(usuarioLogado==null || usuarioLogado.getId()!=idUsuario) {
			log.error("Erro exluir Tarefa - nao autorizado" );
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro exluir Tarefa - nao autorizado");
		}
		
		log.info("Excluir Tarefa; {}" + id);
		tarefaService.excluirTarefa(id);		
		return ResponseEntity.ok("Tarefa excluido com sucesso");
	}

	private Tarefa converterParaTarefa(TarefaDto tarefaDto) {
		
		Tarefa tarefa = new Tarefa();
		tarefa.setId(tarefaDto.getId());
		tarefa.setIdUsuario(tarefaDto.getIdUsuario());
		tarefa.setDescricaoTarefa(tarefaDto.getDescricaoTarefa());
		tarefa.setResumoTarefa(tarefaDto.getResumoTarefa());
		tarefa.setStatus(tarefaDto.getId()==null?StatusTarefaEnum.PENDING.getId():StatusTarefaEnum.COMPLETED.getId());
		tarefa.setDataInclusao(tarefaDto.getId()==null?new Date():tarefaDto.getDataInclusao());
		tarefa.setDataAlteracao(tarefaDto.getId()==null?null:new Date());
		return tarefa;
	}
	
	private ArrayList<TarefaDto> converterParaListaTarefaDto(List<Tarefa> listaTarefa) {
		
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		
		for (Tarefa tarefa : listaTarefa) {
			
			TarefaDto tarefaDto = new TarefaDto();
			tarefaDto.setId(tarefa.getId());
			tarefaDto.setIdUsuario(tarefa.getIdUsuario());
			tarefaDto.setDescricaoTarefa(tarefa.getDescricaoTarefa());
			tarefaDto.setResumoTarefa(tarefa.getResumoTarefa());
			tarefaDto.setStatus(tarefa.getStatus());
			tarefaDto.setDataInclusao(tarefa.getDataInclusao());
			tarefaDto.setDataAlteracao(tarefa.getDataAlteracao());
			tarefaDto.setStatusDescricao(tarefa.getStatus()==0?StatusTarefaEnum.PENDING.getDescricao():StatusTarefaEnum.COMPLETED.getDescricao());
			listaTarefaDto.add(tarefaDto);
		}
		
		return listaTarefaDto;
		
	}	
		
	
}
