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
import org.springframework.http.MediaType;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/controle-tarefa/tarefa")
@CrossOrigin(origins = "*")

@Api(value = "Api Tarefa")
public class TarefaController {

	@Autowired
	TarefaService tarefaService;

	@ApiOperation(value = "Incluir tarefa")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> incluir(@Valid @RequestBody TarefaDto tarefaDto,HttpServletRequest req, HttpServletResponse res, BindingResult result, HttpSession session) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");

		if(usuarioLogado==null||usuarioLogado.getId()!=tarefaDto.getIdUsuario()) {
			log.error("Não foi possivel incluir a tarefa - nao autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		tarefaDto.setIdUsuario(usuarioLogado.getId());

		Tarefa tarefa = this.converterParaTarefa(tarefaDto);

		Response<TarefaDto> response = new Response<TarefaDto>();
		if(result==null) {
			log.error("Não foi possivel incluir a tarefa: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro Incluir Tarefa");
		}

		log.info("Incluir tarefa: {}", tarefaDto.toString());
		log.info("Incluir usuario: {} ", usuarioLogado.getId());
		tarefaService.salvarTarefa(tarefa);

		return ResponseEntity.ok("Tarefa incluida com sucesso");
	}

	@ApiOperation(value = "Listar todas tarefas de todos usuários - apenas ROLE_SUPER_USER")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listarTodasTarefasTodosUsuarios(HttpServletRequest req, HttpServletResponse res, HttpSession session ) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");

		if(usuarioLogado==null||!usuarioLogado.getPerfilUsuario().toString().equals(PerfilUsuarioEnum.ROLE_SUPER_USER.toString())) {
			log.error("Não foi possível listar tarefas de todos usuários - não autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}		

		log.info("Listar tarefas de todos usuários - usuario logado: {} ", usuarioLogado.getId());

		List<Tarefa> listaTarefa = tarefaService.listarTarefasTodosUsuarios();
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		return ResponseEntity.ok(gson.toJson(listaTarefaDto).toString());

	}

	@ApiOperation(value = "Listar todas tarefas do usuário")
	@GetMapping(value="/usuario/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listarTodasTarefasDoUsuario(@PathVariable Long idUsuario,HttpServletRequest req, HttpServletResponse res,HttpSession session) throws Exception {

		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");		

		if(usuarioLogado==null||usuarioLogado.getId()!=idUsuario&&!usuarioLogado.getPerfilUsuario().toString().equals(PerfilUsuarioEnum.ROLE_SUPER_USER.toString())) {
			log.error("Não foi possível listar tarefas do usuário - não autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		log.info("Listar tarefas do usuário - usuario logado: {} ",usuarioLogado.getId());

		List<Tarefa> listaTarefa = tarefaService.listarTarefasPorIdUsuarios(idUsuario);
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();	
		return ResponseEntity.ok(gson.toJson(listaTarefaDto).toString());

	}

	@ApiOperation(value = "Listar todas tarefas do usuário por status")
	@GetMapping(value="/usuario/{idUsuario}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listarTodasTarefasDoUsuarioPorStatus(@PathVariable Long idUsuario,@PathVariable int status,HttpSession session) throws Exception {

		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");

		if(usuarioLogado==null||usuarioLogado.getId()!=idUsuario&&!usuarioLogado.getPerfilUsuario().toString().equals(PerfilUsuarioEnum.ROLE_SUPER_USER.toString())) {
			log.error("Não foi possível listar tarefas do usuario por status - não autorizado");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}

		log.info("Listar tarefas do usuário por status - usuario logado: {} ", usuarioLogado.getId());
		List<Tarefa> listaTarefa = tarefaService.listarTarefasPorIdUsuariosPorStatus(idUsuario,status);
		ArrayList<TarefaDto> listaTarefaDto = new ArrayList<TarefaDto>();
		listaTarefaDto = converterParaListaTarefaDto(listaTarefa);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();	
		return ResponseEntity.ok(gson.toJson(listaTarefaDto).toString());

	}

	@ApiOperation(value = "Alterar dados da tarefa")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<String> alterar(@Valid @RequestBody TarefaDto tarefaDto,HttpServletRequest req, HttpServletResponse res, BindingResult result, HttpSession session ) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");
		Tarefa tarefa = this.converterParaTarefa(tarefaDto);		

		Response<TarefaDto> response = new Response<TarefaDto>();
		if(usuarioLogado==null||usuarioLogado.getId()!=tarefaDto.getIdUsuario()) {
			log.error("Não foi possível alterar a tarefa - não autorizado" );
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não foi possível alterar a tarefa - não autorizado");
		}

		if(result==null) {
			log.error("Não foi possível alterar a tarefa {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível alterar {" + tarefaDto.toString() +"}");
		}
		log.error("Alterar tarefa {} ", tarefaDto.toString() );
		tarefaService.salvarTarefa(tarefa);		
		return ResponseEntity.ok("Tarefa alterada com sucesso");
	}

	@ApiOperation(value = "Excluir tarefa")
	@DeleteMapping(value = "/id/{id}/usuario/{idUsuario}")
	public ResponseEntity<String> exluir(@PathVariable Long id,@PathVariable Long idUsuario, HttpSession session) throws Exception{
		UsuarioDto usuarioLogado = (UsuarioDto) session.getAttribute("usuarioLogado");

		if(usuarioLogado==null || usuarioLogado.getId()!=idUsuario) {
			log.error("Não foi possível exluir tarefa - não autorizado" );
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Não foi possível exluir tarefa - não autorizado");
		}

		log.info("Excluir tarefa; {} ", id);
		log.info("Excluir usuario: {} ", usuarioLogado.getId());
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
