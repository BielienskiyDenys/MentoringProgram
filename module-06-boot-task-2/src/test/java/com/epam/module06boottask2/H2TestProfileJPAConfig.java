package com.epam.module06boottask2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.epam.module06boottask2.repository",
})
@EnableTransactionManagement
public class H2TestProfileJPAConfig {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:unittest;INIT=RUNSCRIPT FROM 'classpath:scripts/populate_test_values.sql'");
        dataSource.setUsername("sa");
        dataSource.setPassword("1");

        return dataSource;
    }

    // configure entityManagerFactory
    // configure transactionManager
    // configure additional Hibernate properties
}
