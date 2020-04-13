package br.com.casadocodigo.loja.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.casadocodigo.loja.dao.UsuarioDAO;



/** Classe com as configurações de segurança
 * 
 * habilita filtro de login
 * 
 */


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UsuarioDAO usuarioDAO;

    //metodo de configuração
    //configura quem pode acesar o quê
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        //** - relaciona todos os itens após a '/'' /
        .antMatchers("/produtos/form").hasRole("ADMIN")
        .antMatchers("/carrinho/**").permitAll()
        .antMatchers("/pagamento/**").permitAll()
        .antMatchers(HttpMethod.GET, "/produtos").permitAll()
        .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
        .antMatchers("/produtos/**").permitAll()
        .antMatchers("/resources/**").permitAll()
        //após config as restrições o que não foi mencionado será liberado
        .antMatchers("/").permitAll()
        .antMatchers("/url-magica-maluca-oajksfbvad6584i57j54f9684nvi658efnoewfmnvowefnoeijn").permitAll()
        .anyRequest().authenticated()
        //setando a pagina de login loginForm.jsp e permitindo a todos
        .and().formLogin().loginPage("/login")
            .defaultSuccessUrl("/produtos").permitAll()
        .and().logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll().logoutSuccessUrl("/login");    
    }

    /** Necessário implementar classes Usuario, UsuarioDAO e Role e implementar
     * metodos para implementar o controle de usuarios no sistema*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDAO)
        .passwordEncoder(new BCryptPasswordEncoder());
    }
}