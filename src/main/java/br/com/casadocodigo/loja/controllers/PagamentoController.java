package br.com.casadocodigo.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.DadosPagamento;

/**
 * Processo de pagamento ocorre como envio de um JSON com index "value" e um valor numérico > {"value":100}
 * 
 * para a URL da "operadora" > http://book-payment.herokuapp.com/payment
 * 
 * */



@RequestMapping("/pagamento")
@Controller
public class PagamentoController {
	
	@Autowired
	private CarrinhoCompras carrinho;
	
	//RestTemplate > faz a integração das apis via http
	//RestTemplate anotado como 'Bean' em WebAppConfiguration p q o Spring reconheça o RestTemplate
	@Autowired
	private RestTemplate restTemplate;
	
	//"/pagamento/finalizar"
	@RequestMapping(value="/finalizar", method = RequestMethod.POST)
														//flash > para enviar info de pagamento OK
	public Callable<ModelAndView> finalizar(RedirectAttributes model) {
		/**REQUISIÇÃO - ASSÍNCRONA
		 * 
		 * Callable auxilia na comunicação 'assincrona' > trabalhando com as rsquests de forma concorrente
		 * > Não espera o final da transação para começar outra
		 * 
		 * Callable é classe anônima e o retorno é uma classe anônima return ()->{}
		 * */
			
			return ()->{
			//setando a url da operadoera
			String uri = "http://book-payment.herokuapp.com/payment";
			
			try {
				/**Add dependencias no pom.xml do 'jackson' para trabalhar com o JSON*/
				//postForObject > envia o Objeto "DadosPagamento/" para a uri informada, e retorna uma String.class 
				String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), 
						String.class);
				
				System.out.println(response);
	
				//parâmetro 'mensagem' é como o valor será esgatao na JSP 
				model.addFlashAttribute("sucesso", response);
				return new ModelAndView("redirect:/produtos");
				
			} catch (HttpClientErrorException e) {
				e.printStackTrace();
				model.addFlashAttribute("falha", "Valor maoir que o permitido");
				return new ModelAndView("redirect:/produtos");
			}	
		}; 			//necessário ";" pois se trata de um 'return'	
	}
}
