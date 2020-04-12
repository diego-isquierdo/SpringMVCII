package br.com.casadocodigo.loja.conf;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/** Concentrando as configurações do JPA de produção */

@Profile("prod")
public class JPAProductionConfiguration {

    //necessário para carregar as variáveis de ambiente
    @Autowired
    private Environment environment;

    @Bean
	public Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	@Bean	
	private DriverManagerDataSource dataSource() throws URISyntaxException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //alterado driver p postgresql
        dataSource.setDriverClassName("org.postgresql.Driver");

        //variavel de ambiente do Heroku para obter USR/PWD do BD
        //devolve -> user:password@host:port/path
        URI dbUrl = new URI(environment.getProperty("DATABASE_URL"));
        
        //setando as configurações do DB com base nos dados fornecidos pelo Heroku
        //através da variável de ambiente "DATABASE_URL" > dbUrl
        dataSource.setUrl("jdbc:postgresql://"+dbUrl.getHost()+":"+dbUrl.getPort()+dbUrl.getPath());
        //utilizado split > info do usr vem em uma mesma String com ':' como separador
        dataSource.setUsername(dbUrl.getUserInfo().split(":")[0]);
        dataSource.setPassword(dbUrl.getUserInfo().split(":")[1]);
    
        return dataSource;
	}
}