package br.com.casadocodigo.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

/**Classeo DAO são responsáveis pelo acesso às entidades do banco
 * 
 * ProdutoDAO trata apenas da entidade Produto
 * */


@Repository
@Transactional
public class ProdutoDAO {

	//persistindo o 'EntityManager' criado junto ao spring
	@PersistenceContext
	private EntityManager manager;
	
	public void gravar(Produto produto) {
		manager.persist(produto);
	}

	//trabalhando com tipos diferentes de preço - formando ujma lista
	public List<Produto> listar() {
		//getResultList() retorna o resultado da busca createQuery em forma de List
		return manager.createQuery("select distinct(p) from Produto p join fetch p.precos", Produto.class)
				.getResultList();
	}

	//Além do produto, trazendo tb os precos > 'fecth join'
	public Produto find(Integer id) {
		//buscando no banco o produto pelo 'id'
		return manager.createQuery("select distinct(p) from Produto p "
				+ "join fetch p.precos preco where p.id = :id", Produto.class)
				.setParameter("id", id)
				.getSingleResult();
	}


	//Relatório de vendas
	public BigDecimal somaPrecosPorTipoPreco(TipoPreco tipoPreco){
		TypedQuery<BigDecimal> query =  manager
			.createQuery("select sum(preco.valor) from Produto p "
			+ "join p.precos preco where preco.tipo = :tipoPreco", BigDecimal.class);
		
			query.setParameter("tipoPreco", tipoPreco);
			return query.getSingleResult();
	}
}