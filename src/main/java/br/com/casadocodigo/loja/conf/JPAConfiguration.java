package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**Classe JPAConfiguration habilida a conexão com o banco de dados e fornece as credencias
 * para acesso;
 * 
 * informações como Driver e dialeto dos protocolos > MySQL, Mariadb, SQL são configurados aqui
 * 
 * Caso haja necessidade de mudança de banco de dados, basta alter Driver e dialetos
 * */

//habilida a transação spring com o banco
@EnableTransactionManagement
public class JPAConfiguration {
	
	//nota como classe Spring
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		
		//credenciais de acesso ao banco
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("#Diego1983#");
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		factoryBean.setDataSource(dataSource);
		
		//propriedades da conexão com o banco - dialetos e Driver em função do tipo de banco > mariadb, MySQL, SQL
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		
		//passando informações ao Spring
		factoryBean.setJpaProperties(properties);
		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		
		
		return factoryBean;
	}

	//classe spring que cria o Entity manager
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}
	
	
}
