/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.configuration;


import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;
import static com.mycompany.mavenassignement.configuration.DatasourceProducer.JNDI_NAME;


/**
 *
 * @author mikael
 */
@Singleton
@DataSourceDefinition(
    name = JNDI_NAME,
    className = "org.h2.jdbcx.JdbcDataSource",
    url = "jdbc:h2:~/sander.db"
)
public class DatasourceProducer {
    public static final String JNDI_NAME =  "java:app/jdbc/postgresql";

    @Resource(lookup = JNDI_NAME)
    DataSource ds;

    @Produces
    public DataSource getDatasource() {
        return ds;
    }
}