package com.ct.controletarefas.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ct.controletarefas.responses.Response;
import com.ct.controletarefas.services.TokenAuthenticationService;
import com.ct.controletarefas.services.UsuarioService;
import com.ct.controletarefas.dtos.UsuarioDto;
import com.ct.controletarefas.entities.Usuario;
import com.ct.controletarefas.filters.UsuarioCredenciais;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/controle-tarefa/auth")
@CrossOrigin(origins = "*")
public class AuthController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Response<UsuarioDto>> auth(@Valid @RequestBody UsuarioCredenciais usuarioCredenciais,HttpServletRequest req, HttpServletResponse res, BindingResult result, HttpSession session) throws Exception{
		
		if(session.getAttribute("usuarioLogado") != null) {
			session.removeAttribute("usuarioLogado");
		}
		
		Usuario usuario = new Usuario();
		usuario = validarAcessoUsuario(usuarioCredenciais, result);
		Response<UsuarioDto> response = new Response<UsuarioDto>();
		if(usuario==null) {
			log.error("Erro validando Auth; {}" + result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		UsuarioDto usuarioDto = this.converterParaUsuarioDto(usuario,res);
		
		response.setData(usuarioDto);
		session.setAttribute("usuarioLogado", usuarioDto);
		return ResponseEntity.ok(response);
	}

	private UsuarioDto converterParaUsuarioDto(Usuario usuario,HttpServletResponse res){
		String token = TokenAuthenticationService.addAuthentication(res,usuario.getEmail());
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setNome(usuario.getNome().toUpperCase());
		usuarioDto.setEmail(usuario.getEmail().toUpperCase());
		usuarioDto.setPerfilUsuario(usuario.getPerfilUsuario());
		usuarioDto.setDataInclusao(usuario.getDataInclusao());
		usuarioDto.setFlAtivo(usuario.getFlAtivo());
		usuarioDto.setToken(token);
		return usuarioDto;
	}

	private Usuario validarAcessoUsuario(UsuarioCredenciais usuarioCredenciais, BindingResult result) {
		Usuario usuario = usuarioService.login(usuarioCredenciais.getEmail(), usuarioCredenciais.getPassword());
		if(usuario==null) {
			result.addError(new ObjectError("Usuario", "Usuário não encontrado"));
			return null;
		} 
		return usuario;
	}
}
