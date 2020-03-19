package br.com.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import javassist.SerialVersionUID;

/**Classe com dados da tabela Role do BD 
 * 
 * processo de autenticação de usuário via Spring
 * necessita implementação - GrantedAuthority
 * 
 * */

@Entity
public class Role implements GrantedAuthority{
		
	private static final long serialVersionUID = 1L;
		
	@Id
    private String nome;
    

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

	@Override
	public String getAuthority() {
		return this.nome;
	}
}