package br.com.casadocodigo.loja.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * Classe Entidade Produto
 * */

//nota a clesse como entidade
@Entity
public class Produto {
	
	//'Id' > nota a PK				'strategy' > passa a gestão do atributo para o BD e seta o Auto Increment
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String titulo;
	private String descricao;
	private int paginas;
	
	//notando o formato da data no spring
	@DateTimeFormat //(pattern = "dd/MM/yyyy") >> atualizado junto a classe AppWebConfig >> mvcConversionService()
	private Calendar dataLancamento;


	//nota a relação via spring entre Preços e Produto formando uma tabela no BD
	@ElementCollection
	private List<Preco> precos = new ArrayList<>();
	
	//String que armazena o caminho para o arquivo
	private String sumarioPath;
	
	
	
	
	public Calendar getDataLancamento() {
		return dataLancamento;
	}
	public void setDataLancamento(Calendar dataLancamento) {
		this.dataLancamento = dataLancamento;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getPaginas() {
		return paginas;
	}
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Preco> getPrecos() {
		return precos;
	}
	
	public void setPrecos(List<Preco> precos) {
		this.precos = precos;
	}
	
	@Override
	public String toString() {
		return "Produto [titulo=" + titulo + ", descricao=" + descricao + ", paginas=" + paginas + "]";
	}
	public String getSumarioPath() {
		return sumarioPath;
	}
	public void setSumarioPath(String sumarioPath) {
		this.sumarioPath = sumarioPath;
	}
	public BigDecimal precoPara(TipoPreco tipoPreco) {
		return precos.stream().filter(preco -> preco.getTipo().equals(tipoPreco))
				.findFirst().get().getValor();
	}

}
