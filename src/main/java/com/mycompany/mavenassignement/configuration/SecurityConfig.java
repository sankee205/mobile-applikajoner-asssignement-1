/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.configuration;


import javax.annotation.security.DeclareRoles;
import javax.annotation.sql.DataSourceDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import com.mycompany.mavenassignement.authentication.Group;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author trygve
 */
@DataSourceDefinition(
        name = "java:global/jdbc/default",
        className = "org.postgresql.ds.PGConnectionPoolDataSource",
        serverName = "localhost",  // set the property
        portNumber = 5432,        // set the property
        databaseName = "postgres",    // set the property
        user = "postgres",
        password = "Telenos2016")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:global/jdbc/default",
        callerQuery="select password from auser where userid = ?",
        groupsQuery="select name from ausergroup where userid  = ?",
        hashAlgorithm = PasswordHash.class,
        priority = 80)
@DeclareRoles({Group.ADMIN,Group.USER})
@LoginConfig(authMethod = "MP-JWT",realmName = "template")
public class SecurityConfig {
    
}