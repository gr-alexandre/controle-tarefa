package com.ct.controletarefas.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ct.controletarefas.entities.Usuario;
import com.ct.controletarefas.enums.PerfilUsuarioEnum;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@After	
	public void tearDowm() throws Exception{
		this.usuarioRepository.deleteAll();
	}
	
	@Test
	public void testFindByEmailAndPassword() {
		Usuario usuario = new Usuario();
		usuario.setId((long) 1);
		usuario.setNome("Teste 01");
		usuario.setEmail("email@email.com");
		usuario.setFlAtivo(1);
		usuario.setPassword("1234");
		usuario.setPerfilUsuario(PerfilUsuarioEnum.ROLE_SUPER_USER);
		usuario.setDataInclusao(new Date());
		usuarioRepository.save(usuario);
		Usuario usuarioRetorno = usuarioRepository.findByEmailAndPassword("email@email.com", "1234");
		
		assertEquals("Teste 01",usuarioRetorno.getNome());
		assertEquals("email@email.com",usuarioRetorno.getEmail());
		assertEquals(1,usuarioRetorno.getId());
		assertEquals(PerfilUsuarioEnum.ROLE_SUPER_USER,usuarioRetorno.getPerfilUsuario());
		assertEquals(1, usuarioRetorno.getFlAtivo());
		
		
	}
}
