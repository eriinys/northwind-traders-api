package com.pluralsight.NorthwindTradersAPI.config;

import ch.qos.logback.classic.BasicConfigurator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DatabaseConfiguration {
    private BasicDataSource basicDataSource;

    @Autowired
    public DatabaseConfiguration(@Value("${datasource.url}") String url,
                                 @Value("${datasource.username}")String user,
                                 @Value("${datasource.password}")String pass){
        basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(pass);
    }

    @Bean
    public DataSource dataSource(){
        return basicDataSource;
    }
}
