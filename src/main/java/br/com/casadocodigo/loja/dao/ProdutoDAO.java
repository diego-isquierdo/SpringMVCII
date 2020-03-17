package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.casadocodigo.loja.models.Produto;

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
		return manager.createQuery("select p from Produto p", Produto.class)
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
}