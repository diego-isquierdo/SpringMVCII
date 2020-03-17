package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

/** No banco de dados, será formada uma tabela paralela relacionando o tipo de preço com 
 * a entidade Produto
 * 
 * */

//Age em conjunto com  a notação "ElementCollection" em 'Produto'
//Forma a tabela paralela no banco que relaciona o tipo de preço com o produto
@Embeddable
public class Preco {

	private BigDecimal valor;
	private TipoPreco tipo;

	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public TipoPreco getTipo() {
		return tipo;
	}

	public void setTipo(TipoPreco tipo) {
		this.tipo = tipo;
	}
}
