package com.hedjerciyakoub.companymanagementwebapp.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"${security.data.jpa.repository.packages}"},
					   entityManagerFactoryRef = "securityEntityManagerFactory",
					   transactionManagerRef = "securityTransactionManager"
		)
public class SecurityDataSourceConfig {
	
	
	@Bean
	@ConfigurationProperties(prefix = "security.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	
	@Bean(name="securityEntityManagerFactory")
	@ConfigurationProperties(prefix = "security.data.jpa.entitys")
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(securityDataSource())
				.build();
	}
	
	@Bean(name="securityTransactionManager")
	public PlatformTransactionManager securityTransactionManager(final @Qualifier("securityEntityManagerFactory")LocalContainerEntityManagerFactoryBean securityManagerFactory) {
		return new JpaTransactionManager(securityManagerFactory.getObject());
	}

}
