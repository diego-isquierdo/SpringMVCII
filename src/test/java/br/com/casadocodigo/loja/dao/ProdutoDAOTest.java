package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.builders.ProdutoBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

/**Os testes não serão gerenciados pelo JUnit, mas sim pelo spring
 * setado em "TunWith"
 * 
 * 
 * */


//config a classe que rodará os testes
@RunWith(SpringJUnit4ClassRunner.class)
//apontando arquivosde conf
@ContextConfiguration(classes= {JPAConfiguration.class, ProdutoDAO.class, DataSourceConfigurationTest.class})
//Anotando no spring uma nova rota para o BD de testes
@ActiveProfiles("test")
public class ProdutoDAOTest{

	@Autowired
	private ProdutoDAO produtoDAO;
	
	//anotação do JUnit
	@Test
	@Transactional //spring
    public void deveSomarTodosPrecosPorTIpoLivro(){
        List<Produto> livrosImpressos = ProdutoBuilder.newProduto(TipoPreco.IMPRESSO, BigDecimal.TEN)
        		.more(3).buildAll();
        
        List<Produto> livrosEbook = ProdutoBuilder
        		.newProduto(TipoPreco.EBOOK, BigDecimal.TEN)
        		.more(3).buildAll();
        livrosImpressos.stream().forEach(produtoDAO::gravar);
        livrosEbook.stream().forEach(produtoDAO::gravar);
        
        BigDecimal valor = produtoDAO.somaPrecosPorTipoPreco(TipoPreco.EBOOK);
        Assert.assertEquals(new BigDecimal(40).setScale(2), valor); 
        
    }

}