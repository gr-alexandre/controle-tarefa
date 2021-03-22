package com.ct.controletarefas.services;


import com.ct.controletarefas.entities.Usuario;

public interface UsuarioService {
	
	Usuario login(String email, String password);	
	
}
