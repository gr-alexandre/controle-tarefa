package com.ct.controletarefas.filters;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioCredenciais {
	
	@NotEmpty(message = "E-mail não pode ser vazio")
	@Length(min = 5, max = 255, message = "E-mail deve conter entre 5 e 255 caracteres")
	@Email(message = "E-mail inválido")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazio")
	@Length(min = 3, max = 10, message = "Senha deve conter entre 3 e 10 caracteres")
	private String password;

}
