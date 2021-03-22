package com.ct.controletarefas.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum StatusTarefaEnum {
	PENDING(0,"Pending"),
	COMPLETED(1,"Completed");
	
	private final int id;
	private final String descricao;

}
