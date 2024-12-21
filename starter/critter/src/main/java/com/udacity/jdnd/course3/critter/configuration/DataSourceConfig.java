package com.udacity.jdnd.course3.critter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {

        @Bean
        @ConfigurationProperties(prefix="com.udacity.datasource")
        public DataSource getDataSource() {
            DataSourceBuilder dsb = DataSourceBuilder.create();
            dsb.url("jdbc:mysql://localhost:3306/critter");
            return dsb.build();
        }
    }


