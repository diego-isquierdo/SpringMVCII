package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

public class DadosPagamento {
	public DadosPagamento(BigDecimal value) {
		this.value = value;
	}
	//criando construtor padrão para q o objeto possa ser incializado mesmo sem valores
	public DadosPagamento() {
	}
	
	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}
	
}
