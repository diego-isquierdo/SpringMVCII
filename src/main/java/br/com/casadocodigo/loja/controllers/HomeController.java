package br.com.casadocodigo.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.models.Produto;


//nota a classe como Controller da páina 'home'
@Controller
public class HomeController {

    @Autowired
    ProdutoDAO produtoDao;

	@RequestMapping("/")
	//habilitando o cache do BD - Precisa anotar AppWebConfiguration com @EnableCaching - habilita o cache
	@Cacheable(value = "produtosHome")
    public ModelAndView index(){
        List<Produto> produtos = produtoDao.listar();
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("produtos", produtos);
        return modelAndView;
    }
}
