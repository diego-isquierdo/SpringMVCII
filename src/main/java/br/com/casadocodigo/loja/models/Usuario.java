package br.com.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**Classe para fornecer os dados para a tablea usuario no BD 
 * 
 * Objeto Usuário fornece as credenciais para acesso à apliocação
 * 
 * Os métodos Override são adicionados por implementar UserDatails
 * 
*/

@Entity
public class Usuario implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    private String email;
	private String senha;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Role> roles = new ArrayList<Role>();

    
   
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getPermissoes() {
        return roles;
    }

    public void setPermissoes(List<Role> permissoes) {
        this.roles = permissoes;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
	//implementados em função do UserDatails
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.senha;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	//conta não expira
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//conta não bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//credenciais não expira
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//conta habilitada
	@Override
	public boolean isEnabled() {
		return true;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}