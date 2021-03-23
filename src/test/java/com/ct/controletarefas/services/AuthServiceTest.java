package com.ct.controletarefas.services;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.ct.controletarefas.entities.Usuario;
import com.ct.controletarefas.repositories.UsuarioRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

	@MockBean
	UsuarioRepository usuarioRepository;

	@Autowired
	UsuarioService usuarioService;

	@Test
	public void testLogin() {
		BDDMockito.given(this.usuarioRepository.findByEmailAndPassword(Mockito.anyString(),Mockito.anyString())).willReturn(new Usuario());	

		Usuario usuario = this.usuarioService.login("email@emai.com", "123");
		assertNotNull(usuario);
	}

}
