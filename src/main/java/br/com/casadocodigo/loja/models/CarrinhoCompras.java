package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**Anotar junto ao AppWebConfiguration - ComponentScann - por ser um componente Spring*/

//anotação genérica que indica um componente gerenciado pelo Spring
//Classes que não Controllers nem DAO, mas que fornecem informação para as JSP
@Component
/**Anotações do tipo 'Scope' ajudam a informar ao Spring como será a estrutura da aplicação
 * 
 * No caso, é informado que o escopo da aplicação é por sessão
 * ou seja, cada usuário terá a sua sessão, o seu carrinho
 * O 'CONTORLLER' da Classe tb precisa ser anotado
 * 
 * 
 * O ideal seria anotar o 'scope_session' em todos os Cotnrollers para anotar todos como multi sessão
 * porém, criando um 'proxyMode' já resolve, e o spring controla as  multi sessões nas outras classes*/
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS) 
//criando um proxy 


//multi sessão > implementar o serialização da classe para q o objeto seja diferenciado
public class CarrinhoCompras implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//criando a lista de compras do carrinho
	private Map<CarrinhoItem, Integer> itens = new LinkedHashMap<CarrinhoItem, Integer>();
	
	public void add(CarrinhoItem item) {
		itens.put(item, getQuantidade(item) +1);
	}

	public Collection<CarrinhoItem> getItens() {
		return itens.keySet();
	}
	
	
	//verifica se o 'CarrinhoItem' está na lista 'item' > caso caso não, adiciona o mesmo e o retorna
	public Integer getQuantidade(CarrinhoItem item) {
		if(!itens.containsKey(item)) { 
			itens.put(item, 0);
		}
		return itens.get(item);
	}
	
	public int getQuantidade() {
		return itens.values().stream().reduce(0, 
				(proximo, acumulador) -> proximo +acumulador);
	}
	
	public BigDecimal getTotal(CarrinhoItem item) {
		return item.getTotal(getQuantidade(item));
	}
	
	public BigDecimal getTotal() {
		//inicializando um BD com zero
		BigDecimal total = BigDecimal.ZERO;
		//keySet() > são as chaves do array
		//no Hash Map > as referencias dos valores são setados manualmente
		for (CarrinhoItem item : itens.keySet()) {
			total = total.add(getTotal(item));
		}
		return total;
	}

	public void remover(Integer produtoId, TipoPreco tipoPreco) {
		Produto produto = new Produto();
		//recuperando o produto pelo id passado > vai criar um objeto com Id de um produto do BD
		produto.setId(produtoId);
		System.out.println(produto.getTitulo() + " " + tipoPreco);
		itens.remove(new CarrinhoItem(produto, tipoPreco));
	}
}
