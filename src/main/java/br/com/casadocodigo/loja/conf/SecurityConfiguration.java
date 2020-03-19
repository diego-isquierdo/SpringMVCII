package br.com.casadocodigo.loja.conf;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;



/** Classe com as configurações de segurança
 * 
 * habilita filtro de login
 * 
 */


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    //metodo de configuração
    //configura quem pode acesar o quê
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/resources/**").permitAll()
        .antMatchers("/carrinho/**").permitAll()
        .antMatchers("/pagamento/**").permitAll()
        .antMatchers("/produtos/form").hasRole("ADMIN")
        .antMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/produtos").hasRole("ADMIN")
        .antMatchers("/produtos/**").permitAll()
        .antMatchers("/").permitAll()
        .anyRequest().authenticated()
        .and().formLogin();
    }
}