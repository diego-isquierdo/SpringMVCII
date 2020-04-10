package br.com.casadocodigo.loja.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import javax.servlet.Filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;
import br.com.casadocodigo.loja.conf.SpringSecurityFilterConfiguration;
import br.com.casadocodigo.loja.dao.ProdutoDAO;


/**
 * Classe de testes com a pg '/' - home
 * 
 * para fins de testes, comentar a anotação @EnableCaching em AppWebConfiguration 
 * 
 * */


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, AppWebConfiguration.class, 
		DataSourceConfigurationTest.class, SecurityConfiguration.class})
@ActiveProfiles("test")
public class ProdutosControllerTest {
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private Filter SpringSecurityFilterChain;
	
	private MockMvc mockMvc;
	
	//anotando para ser executado antes
	@Before
	public void setup(){
	    mockMvc = MockMvcBuilders.webAppContextSetup(wac)
	    		.addFilters(SpringSecurityFilterChain)
	    		.build();
	}
	
	@Test
	public void deveRetornarParaHomeComOsLivros() throws Exception{
	    mockMvc.perform(MockMvcRequestBuilders.get("/"))
	    		//verifica se o retorno ainda tras um atributo 'produto'
	            .andExpect(MockMvcResultMatchers.model().attributeExists("produtos"))
	            //verifica se o '/' aponta de fato para o uri
	            .andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/home.jsp"));
	}
	
	@Test
	public void somenteAdminDeveAcessarProdutosForm() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/produtos/form")
				//Usando parte de scurity do MVC - add dependencia no pom.xml
				.with(SecurityMockMvcRequestPostProcessors
						.user("user@casadocodigo.com.br").password("123456")
						//testanto s usr USUARIO eh bloqueado ao acessar - ADMIN retorna 200
						.roles("USUARIO")))
				.andExpect(MockMvcResultMatchers.status().is(403));
	}
	
}
