package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/** Copia da classe Original JPA Configuration
 * 
 * Original foi alterada pa deploy no heroku
 */

/**
 * Classe JPAConfiguration habilida a conexão com o banco de dados e fornece as
 * credencias para acesso;
 * 
 * informações como Driver e dialeto dos protocolos > MySQL, Mariadb, SQL são
 * configurados aqui
 * 
 * Caso haja necessidade de mudança de banco de dados, basta alter Driver e
 * dialetos
 */

//habilida a transação spring com o banco
@EnableTransactionManagement
public class JPAConfigurationCopy {

	// nota como classe Spring
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factoryBean.setJpaVendorAdapter(vendorAdapter);

		/**
		 * Atualizado o cod para chamar o metodo dataSourvce com as credenciais do BD
		 */

		factoryBean.setDataSource(dataSource);

		// passando informações ao Spring
		factoryBean.setJpaProperties(additionalProperties());

		// propriedades da conexão com o banco - dialetos e Driver em função do tipo de
		// banco > mariadb, MySQL, SQL
//		Properties properties = additionalProperties(); > atualizado codigo

		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

		return factoryBean;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		return properties;
	}

	// separando as credenciais do banco para evitar reescrita
	// este trecho do cod tb será acessado pela classe de testes
	@Bean
	@Profile("dev")
	private DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername("root");
		dataSource.setPassword("#Diego1983#");
		dataSource.setUrl("jdbc:mysql://localhost/casadocodigo");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return dataSource;
	}

	// classe spring que cria o Entity manager
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

}
