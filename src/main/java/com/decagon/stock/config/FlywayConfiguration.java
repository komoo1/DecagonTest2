package com.decagon.stock.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Victor.Komolafe
 */
@Configuration
public class FlywayConfiguration {

    @Autowired
    public FlywayConfiguration(DataSource dataSource) {
        Flyway.configure().baselineOnMigrate(true)
                .validateOnMigrate(true)
                .dataSource(dataSource).load().migrate();
    }
}
