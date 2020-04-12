package br.com.casadocodigo.loja.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.jmx.export.metadata.ManagedResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import br.com.casadocodigo.loja.controllers.HomeController;
import br.com.casadocodigo.loja.dao.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.CarrinhoCompras;

/**
 * Classe base da Aplicação
 * 
 * Faz o direcionamento inicial do fluxo da aplicação
 * */


//habilita o Spring WEB
@EnableWebMvc
//informa as classes (e seus pacotes) onde estarão os controllers de acesso - Aplicação e Banco
//todos os pacotes úteis para a aplicação devem ser informados 
@ComponentScan(basePackageClasses= {HomeController.class, ProdutoDAO.class, 
		FileSaver.class, CarrinhoCompras.class})

//habilita o cache		
@EnableCaching //>>>comentar essa notação para testes no JUnit
//WebMvcConfigurerAdapter - auxilia no mapeamento dos arquivos js e css
public class AppWebConfiguration extends WebMvcConfigurerAdapter {
	
	//prefixo "/WEB-INF/views/" e sufixo  ".jsp"
	@Bean //classe spring q auxilia no mapeamento das paginas
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix( ".jsp");
		
		//Spring "disponibiliza" todos os Beans para os JSPs > JSP conseguem acessar as classes 'Bean'
		//resolver.setExposeContextBeansAsAttributes(true);
		
		//Disponibilizando pontualmente os Beans > melhor em termos de segurança
		//Informar por String o nome da Classe com a primeira letra 'minúscula'
		resolver.setExposedContextBeanNames("carrinhoCompras");
		
		
		return resolver;
	}
	
	@Bean
	//configurandoo arquivo padrão de mensagens >> message.proprieties
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource 
		= new ReloadableResourceBundleMessageSource();
		
		//apontando o caminho para a o arquivo com as mensagens de erro > arquivo - 'messages'.*
		messageSource.setBasename("/WEB-INF/messages");
		//setando o padrão de encoding
		messageSource.setDefaultEncoding("UTF-8");
		//o tempo q o arquivo atualiza quando uma alteração é feita
		messageSource.setCacheSeconds(1);
		
		return messageSource;
	}
	
	//definindo as configurações de data
	//notações ao longo do codigo não serão mais necessárias
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		DateFormatterRegistrar registrar = new DateFormatterRegistrar();
		registrar.setFormatter(new DateFormatter("dd/MM/yyyy"));
		registrar.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	//crianto metodo para tratar o multpart
	public MultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
	
	//anotando o RestTemplate para Spring Reconhece-lo
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	//metodo que cria o cache - CacheManager não pode ser usado um produção
	//cache em produção utilizar o 'Guava' do Google
	@Bean
	public CacheManager cacheManager(){
		/** configurando o Cache
		 * - ativo por 5 minutos
		 * - guarda até 100 elementos
		 * Obs.: config básica p testes em desenvolvimento
		 */
		CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder()
			.maximumSize(100)
			.expireAfterAccess(5, TimeUnit.MINUTES);
		GuavaCacheManager manager = new GuavaCacheManager();
		manager.setCacheBuilder(builder);

		return manager;
	}

	//configurando a Resposta do sistema. Cofnigurar se o mesmo vai responder
	//como HTML ou JSON
	@Bean
	public ViewResolver contentNegociationViewResolver(ContentNegotiationManager manager){
		//criando uma lista de ViewResolver - HTML, JSON
		List<ViewResolver> viewResolvers = new ArrayList<>();
		//resolve o HTML
		viewResolvers.add(internalResourceViewResolver());
		//resolve o JSON
		viewResolvers.add(new JsonViewResolver());

		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setViewResolvers(viewResolvers);
		resolver.setContentNegotiationManager(manager);

		return resolver;
	}

	//aponta qual servlet vai mapear as requisições default para arquivos js/css
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
	}


	//Config para alterar a linguagem da página
	//interceptar e
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	//passando para o spring q será resolvido a linguagem em forma de cookie
	@Bean
	public LocaleResolver localeResolver(){
		return new CookieLocaleResolver();
	}

	@Bean
	public MailSender mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("vellhojoaquimteixeira@gmail.com");
		mailSender.setPassword("#sextou#pas");
		mailSender.setPort(587);
	
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
	
		mailSender.setJavaMailProperties(mailProperties);
		return mailSender;
	}

}
	