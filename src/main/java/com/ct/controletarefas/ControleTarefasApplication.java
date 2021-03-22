package com.ct.controletarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.ct.controletarefas", "com.ct.controletarefas.*"})
@EnableCaching
public class ControleTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleTarefasApplication.class, args);
	}	

}
