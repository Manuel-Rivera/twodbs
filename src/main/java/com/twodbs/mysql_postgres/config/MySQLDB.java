package com.twodbs.mysql_postgres.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
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
basePackages = {"com.twodbs.mysql_postgres.repository.mysql"}
)
public class MySQLDB {
    private final Environment env;

    public MySQLDB(Environment env) {
        this.env = env;
    }

    @Primary
    @Bean(name = "mysqlDataSource")
    @ConfigurationProperties(prefix = "mysql.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("mysqlDataSource") DataSource dataSource){
        //Add configuration session
        HikariDataSource hirakiDataSource = new HikariDataSource();
        hirakiDataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("mysql.hikari.maximum-pool-size"))));
        hirakiDataSource.setConnectionTimeout(Long.parseLong(Objects.requireNonNull(env.getProperty("mysql.hikari.connection-timeout"))));
        hirakiDataSource.setIdleTimeout(Long.parseLong(Objects.requireNonNull(env.getProperty("mysql.hikari.idle-timeout"))));

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(hirakiDataSource);
        factoryBean.setDataSource(dataSource);

        factoryBean.setPackagesToScan("com.twodbs.mysql_postgres.entity.mysql");
        HibernateJpaVendorAdapter vendor=new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendor);
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("mysql.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("mysql.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql",env.getProperty("mysql.hibernate.show_sql"));
        properties.put("hibernate.format_sql",env.getProperty("mysql.hibernate.format_sql"));
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }
    @Primary
    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
