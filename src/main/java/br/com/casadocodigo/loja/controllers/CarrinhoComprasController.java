package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
/**Anotando a Classe sobre o escopo de 'Sessão'
 * No caso, anotado o SCOPE_REQUEST para atender a mais de 1 request
 * e será possível ter multi sessão e um carrinho para cada usr
 * 
 * Informação do Estrutor sobre Escopo
 * Por padrão, o Spring define que o escopo de todos os componentes é de application, 
 * ou seja, apenas uma instância existe desde quando a aplicação foi criada.
 *  O principal problema dessa abordagem é esse que acabamos de encontrar, 
 *  os usuários compartilham sempre os mesmos dados, uma alteração afeta 
 *  todo mundo que está conectado à nossa aplicação. 
 *  Para resolver isso, precisamos mudar o escopo do nosso carrinho.
 * 
 * O primeiro escopo apresentado é o escopo de aplicação (SCOPE_APPLICATION), isto é, desde que o servidor é iniciado, 
 * apenas um objeto na memória é manipulado, o que causa conflito quando temos mais de um usuário usando a nossa aplicação. 
 * O segundo escopo é o de sessão (SCOPE_GLOBAL_SESSION), no qual o objeto é criado para cada usuário que se conecta à aplicação, 
 * ou seja, usuários diferentes usam objetos diferentes, o que é ideal para um carrinho de compras, 
 * uma vez que cada usuário possui o seu próprio carrinho. 
 * O último escopo apresentado é o escopo de request (SCOPE_REQUEST), no qual cada vez que acessamos a página, um objeto é criado.
 * 
 * */
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDao;
	
	@Autowired
	private CarrinhoCompras carrinho;

	//Controller já está mapeado com "/carrinho" - quando o '/carrinho' for acessado via 'GET'
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens() {
		return new ModelAndView("carrinho/itens");
	}
	
	@RequestMapping("/add")
	//os parâmetros recebidos de 'detalhe.jsp' são os que serão enviados ao /carrinho/add
	//no caso.. será recebido o id do produto e o tipo do preco
	public ModelAndView add(Integer produtoId, TipoPreco tipoPreco) {
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		
		CarrinhoItem carrinhoItem = criaItaem(produtoId, tipoPreco);
		carrinho.add(carrinhoItem);
		
				
		return modelAndView;
	}

	
	private CarrinhoItem criaItaem(Integer produtoId, TipoPreco tipoPreco) {
		//usando o produtoDAO para buscar o poroduto pelo id
		Produto produto = produtoDao.find(produtoId);
		
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipoPreco);
		
		return carrinhoItem;
	}
	
	@RequestMapping("/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco) {
		carrinho.remover(produtoId, tipoPreco);
		
		return new ModelAndView("redirect:/carrinho");
	}
}
