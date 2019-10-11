package com.fis.thread.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author PhucVM3
 * @date 26/04/2018 13:58:00
 */
@Configuration
@EnableTransactionManagement
@PropertySource("file:../etc/database.properties")
public class DBConfig {

	private final Logger logger = LoggerFactory.getLogger(DBConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	private ResourceLoader rl;

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(dataSource());
		// lsfb.setPackagesToScan("com.fis.*");
		// lsfb.setMappingLocations(loadResources());
		lsfb.setHibernateProperties(hibernateProperties());
		try {
			lsfb.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsfb.getObject();
	}
	
	@Bean
	public DataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
		hikariConfig.setJdbcUrl(env.getRequiredProperty("jdbc.url"));
		hikariConfig.setUsername(env.getProperty("jdbc.username"));
		hikariConfig.setPassword(env.getProperty("jdbc.password"));

		hikariConfig
				.setMaximumPoolSize(Integer.parseInt(env.getProperty("spring.datasource.hikari.maximum-pool-size")));
		hikariConfig.setConnectionTestQuery("SELECT 1 from dual ");
		hikariConfig.setPoolName(env.getProperty("spring.datasource.hikari.pool-name"));

		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");

		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		dataSource.getHikariPoolMXBean();

		return dataSource;
	}

	@Bean
	public HibernateTemplate hibernateTemplate() {
		return new HibernateTemplate(sessionFactory());
	}

	@Bean
	public HibernateTransactionManager hibernateTransactionManager() {
		return new HibernateTransactionManager(sessionFactory());
	}

	public Resource[] loadResources() {
		Resource[] resources = null;
		try {
			resources = ResourcePatternUtils.getResourcePatternResolver(rl)
					.getResources("classpath:/hibernate/*.hbm.xml");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return resources;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		// properties.put("hibernate.id.new_generator_mappings",
		// env.getProperty("hibernate.id.new_generator_mappings"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
		return properties;
	}
}
