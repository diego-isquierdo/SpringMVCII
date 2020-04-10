package br.com.casadocodigo.loja.conf;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**Para evitar alterações na classe JPA principal
 * 
 * Esta classe foi configurada para acessar o BD de teste
 * 
 * e fazer os teste sem afetar o cod principal
 * 
 * */


public class DataSourceConfigurationTest {
	
	//setando o profile como test e o spring faz a ponte 
	//entre as classers setadas como 'test'
	@Bean
	@Profile("test")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo_test");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUsername("root");
		dataSource.setPassword("#Diego1983#");
		
		return dataSource;
	}
}
