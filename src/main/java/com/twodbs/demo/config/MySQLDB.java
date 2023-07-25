package com.twodbs.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( entityManagerFactoryRef = "mysqlEntityManagerFactory",
transactionManagerRef = "mysqlTransactionManager",
basePackages = {"com.twodbs.demo.repository.mysql"}
)
public class MySQLDB {
    @Value("${mysql.jpa.database-plataform}")
    private String dialect;
    
    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("mysqlDataSource") DataSource dataSource){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPackagesToScan("com.twodbs.demo.entity.mysql");
        HibernateJpaVendorAdapter vendor=new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendor);
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernete.dialect", dialect);
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }

    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
