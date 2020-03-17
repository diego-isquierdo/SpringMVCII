package br.com.casadocodigo.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.casadocodigo.loja.models.Produto;

/**
 * Extendendo interface Validator o que torna a classe um validator
 * */

public class ProdutoValidation implements Validator {

	//metodo faz a validação se esta classe suporta ser um validator
	//Tb eh uma forma de assinar a classe como 'Validator'
	@Override
	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	
	
	//metodo faz o tratamento dos atributos recebidos via formulário POST
	//onde os campos devem ter vindo preechidos
	@Override											//Errors - Objeto do Spring que auzilia na tratativa de erros
	public void validate(Object target, Errors errors) {
		//ValidationUtils trata o atributo "titulo" e verifica se ele está preenchido - 'fiel.required'
		ValidationUtils.rejectIfEmpty(errors, "titulo", "field.required");
		ValidationUtils.rejectIfEmpty(errors, "descricao", "field.required");
		
		//fazendo um cast para tratar especificamento de um 'Produto'
		Produto produto = (Produto) target;
		//'paginas' não pode ser 0 ou negativo
		if(produto.getPaginas() <= 0) errors.rejectValue("paginas", "field.required");		
		
		//Não eh necessário 'return' pois a Classe Errors armazena o resultado 
	}
}
