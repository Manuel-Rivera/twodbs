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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( entityManagerFactoryRef = "postgresEntityManagerFactory",
        transactionManagerRef = "postgresTransactionManager",
        basePackages = {"com.twodbs.mysql_postgres.repository.postgres"}
)
public class PostgresDB {
    private final Environment env;

    public PostgresDB(Environment env) {
        this.env = env;
    }

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "postgres.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    //Mysql JDBC Template
    @Bean(name = "postgresJdbcTemplate")
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
    @Bean(name = "postgresNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate postgresNamedParameterJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }

    //postgres configuration from hibernate
    @Bean(name = "postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("postgresDataSource") DataSource dataSource){
        //Add configuration session
        HikariDataSource hirakiDataSource = new HikariDataSource();
        hirakiDataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("postgres.hikari.maximum-pool-size"))));
        hirakiDataSource.setConnectionTimeout(Long.parseLong(Objects.requireNonNull(env.getProperty("postgres.hikari.connection-timeout"))));
        hirakiDataSource.setIdleTimeout(Long.parseLong(Objects.requireNonNull(env.getProperty("postgres.hikari.idle-timeout"))));

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(hirakiDataSource);
        factoryBean.setDataSource(dataSource);

        factoryBean.setPackagesToScan("com.twodbs.mysql_postgres.entity.postgres");
        HibernateJpaVendorAdapter vendor=new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(vendor);
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", env.getProperty("postgres.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("postgres.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql",env.getProperty("postgres.hibernate.show_sql"));
        properties.put("hibernate.format_sql",env.getProperty("postgres.hibernate.format_sql"));
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }
    @Primary
    @Bean(name = "postgresTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
}
