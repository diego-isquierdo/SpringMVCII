package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;


/**
 * Classe Controller relacionada à produtos
 * 
 * Lista/Gravação ou formulários são controlados por esta Classe
 * 
 * */

//nota a classe como controller
@Controller
//nota um auxílio no mapeamento acrescentando 'produtos' ao caminho das pg >> casadocodigo/'produtos'/...
@RequestMapping("/produtos")
public class ProdutosController {
	
	//spring aciona um produtoDAO
	@Autowired
	private ProdutoDAO dao;
	
	//Spring injetando o fileSaver para salvar o arquivo de upload
	@Autowired
	private FileSaver fileSaver;
	
	//Habilitando junto ao Spring o ProdutoValidation
	//É necessário em virtude do 'Bind' que o Spring faz - varre a classe Produto e compara os atributos da classe
	//com os atributos passados via POST >> 'titulo' x 'titulo' >> 'descricao' x 'descrcao' ...
	@InitBinder //inicializa o metodo
	public void initBinder(WebDataBinder binder) {
		//o initBinder já espera um objeto 'WebDataBinder'
		
		binder.addValidators(new ProdutoValidation());
	}
	
	
	
	//mapeia a pg 'form'
	@RequestMapping("/form")
	//passando o Produto para o jstl no JSP tratar os atributos
	public ModelAndView form(Produto produto) {
		//ModelAndView > permite o envio de parâmetros para as páginas JSP
		//o endereço das páginas são passados por parâmetros na criação do Objeto
		ModelAndView modelAndView = new ModelAndView("produtos/form");
		
		//add os parâmetros em modelAndView > na pg JSP poderão ser lidos pelo atributo 'tipos'
		modelAndView.addObject("tipos", TipoPreco.values());
		
		return modelAndView;
	}
	
	
	//mapeia 'produtos' (mapeado na construção da classe) passado por POST > via formulário JSP
	@RequestMapping(method=RequestMethod.POST)
	//ModelAndView jpa espera por um RedirectAttributes para tratar a possibilidade 
	//de duplicar via F5 o envio de formulario
	//Obs.: o metodo deve receber os parâmetros nesta ordem. senão não rola
	//MultpartFile > tratando o arquivo que virá por upload
	@CacheEvict(value = "produtosHome", allEntries = true) //>>> limpa o cache antes de gravar no novo item - Atualiza o cache	
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes){
		//BindingResult já possui o resultado de totdo o processo de validação - Validator
		
		//feita a verificação, retorno o usr p o formulario caso haja erro
		if (result.hasErrors()) return form(produto);
		
		//getOriginalFilename > recupera o nome original do arquivo	
		//System.out.println(sumario.getOriginalFilename());
		
		//tratando de salvar o arquivo enviado
	    String path = fileSaver.write("arquivos-sumario", sumario);
		
		System.out.println("ProdutosController - path > " + sumario);
		
		//informando para o BD o caminho do diretorio onde ficou salvo o arquivo
		produto.setSumarioPath(path);
				
		dao.gravar(produto);
		//FlashAtribute > dura apenas entre uma request e outra
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!");
		
		//passado para o JSP o pedido de redirecionamento (302) para mudar a request
		//o ação eh feita para evitar duplicidade no envio de um formularioa e gravar 
		//informação duplicada no BD
		return new ModelAndView("redirect:produtos");
	}
	
	
	//mapeia 'produtos' (mapeado na construção da classe) passado por GET
	@RequestMapping( method=RequestMethod.GET)
	public ModelAndView listar() {
		//faze a leitura do BD e envia para a JSP os parâmetros via 'modelAndView'
		List<Produto> produtos = dao.listar();
		//informa o destino ao 'modelAndView' e add o nome do atributo JSP - 'produtos' e o Object Produto
		ModelAndView modelAndView = new ModelAndView("produtos/lista");
		modelAndView.addObject("produtos", produtos);
		
		return modelAndView;
	}
	
	//uso de 'url amigável' para auxiliar no mapeamento
	@RequestMapping("detalhe/{id}")
	//PathVariable complementa com a url amigável e fornece o 'id' e complementa a url
	public ModelAndView detalhe(@PathVariable("id") int id){
	    ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
	    Produto produto = dao.find(id);
		modelAndView.addObject("produto", produto);
	    System.out.println(produto);
	    return modelAndView;
	}


}
