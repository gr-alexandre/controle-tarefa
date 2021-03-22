package com.ct.controletarefas.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ct.controletarefas.entities.Usuario;
import com.ct.controletarefas.repositories.UsuarioRepository;
import com.ct.controletarefas.services.UsuarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario login(String email, String password) {
		log.info("Buscar para o login email: {}", email);
		return usuarioRepository.findByEmailAndPassword(email, password);
	}

}
