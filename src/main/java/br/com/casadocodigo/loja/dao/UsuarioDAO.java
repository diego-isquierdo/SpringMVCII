package br.com.casadocodigo.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Usuario;

/**Classe que fará a persistência do 'Usuário' no BD
 * 
 * Necessário que tanto o Usuario quanto UsuarioDAO
 * Implementem UserDetailsService
 * 
 */


@Repository
public class UsuarioDAO implements UserDetailsService{

    @PersistenceContext
    private EntityManager manager;

    public void gravar(Usuario usuario){
        manager.persist(usuario);
    }

    public Usuario loadUserByUsername(String email){
        List<Usuario> usuarios = manager
                .createQuery("select u from Usuario u where u.email = :email",
                Usuario.class)
            .setParameter("email", email)
            .getResultList();
        
        if(usuarios.isEmpty()){
            throw new UsernameNotFoundException("Usuario " + email + " não foi encontrado!");
        }

        return usuarios.get(0);
    }
    
}