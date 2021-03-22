package com.ct.controletarefas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ct.controletarefas.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	@Transactional(readOnly = true)
	Usuario findByEmailAndPassword(String email,String password);	
}
