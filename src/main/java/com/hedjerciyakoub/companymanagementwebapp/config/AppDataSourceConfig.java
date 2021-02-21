package com.hedjerciyakoub.companymanagementwebapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableJpaRepositories(basePackages = {"${app.data.jpa.repository.packages}"} ,
					   entityManagerFactoryRef = "appEntityManagerFactory" ,
					   transactionManagerRef = "appTransactionManager"
		)
public class AppDataSourceConfig implements WebMvcConfigurer {


	@Primary
	@Bean
	@ConfigurationProperties(prefix = "app.datasource")
	public DataSource appDateSource() {
		return DataSourceBuilder.create().build();
	}
	
		
	@Primary
	@Bean(name="appEntityManagerFactory")
	@ConfigurationProperties(prefix = "app.data.jpa.entitys")
	public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(EntityManagerFactoryBuilder builder) {

		return builder
				.dataSource(appDateSource())
				.build();
	}
	
	
	@Primary
	@Bean(name="appTransactionManager")
	public PlatformTransactionManager appTransactionManager(final @Qualifier("appEntityManagerFactory")LocalContainerEntityManagerFactoryBean appManagerFactory) {
		return new JpaTransactionManager(appManagerFactory.getObject());
	}

	
//    public void addFormatters(FormatterRegistry formatterRegistry) {
//        formatterRegistry.addFormatter(new WorOnFormatter());
//    }
	
}
