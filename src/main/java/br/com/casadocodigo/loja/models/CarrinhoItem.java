package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;

//para trabalhar com mais de um objeto, implementar Serializable
public class CarrinhoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto;
	private TipoPreco tipoPreco;
	
	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		this.produto = produto;
		this.tipoPreco = tipoPreco;
	}
	
	//retornando o preco do item para o 'itens.jsp'
	public BigDecimal getPreco() {
		return produto.precoPara(tipoPreco);
	}
	
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	//retornando o valor total do carrinho
	public BigDecimal getTotal(int quantidade) {
		return this.getPreco().multiply(new BigDecimal(quantidade));
	}
}
