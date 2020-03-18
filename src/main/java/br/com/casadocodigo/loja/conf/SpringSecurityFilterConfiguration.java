package br.com.casadocodigo.loja.conf;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/** Classe criada para tratar da segurança da aplicação
 * 
 * Filtros de usuários para determinadas parte do projeto
 * 
 * add ao pom.xml as dependencias referentes ao Spring Security
 *  -> classe abstrata 'AbstractSecurityWebApplicationInitializer' parte dessas dep.
 * 
 * criada outra classe - SecurityConfiguration com as configurações do 'Security'
 */

public class SpringSecurityFilterConfiguration extends AbstractSecurityWebApplicationInitializer{

}