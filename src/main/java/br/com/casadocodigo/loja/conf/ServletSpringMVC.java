package br.com.casadocodigo.loja.conf;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Primeira Classe da aplicação.
 * 
 * Extende a interface de inicialização do spring, e redireciona o fluxo da aplicação
 * 
 * */



public class ServletSpringMVC extends AbstractAnnotationConfigDispatcherServletInitializer{

	//configurações de segurança do Spring
	@Override
	protected Class<?>[] getRootConfigClasses() {
		//Array de classes de configuração 
		return new Class[]{SecurityConfiguration.class,AppWebConfiguration.class, JPAConfiguration.class};
	}

	//determina as próximas classes de configuração - 01 > aplicação e 01 > Banco de Dados
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {};
	}
	
	//Estabelece o diretório '/' como base inicial da aplicação
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	//classe que aplica os filtros de coding garantido a integridade dos informação grava e lida no banco
	@Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        return new Filter[] {encodingFilter};
    }
	
	//tratando o multipart
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}
}
