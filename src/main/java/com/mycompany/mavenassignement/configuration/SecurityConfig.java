/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.configuration;


import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.PasswordHash;
import com.mycompany.mavenassignement.authentication.Group;
import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author trygve
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:app/jdbc/postgresql",
        callerQuery="select password from user where id = ?",
        groupsQuery="select name from ausergroup where id  = ?",
        hashAlgorithm = PasswordHash.class,
        priority = 80)
@DeclareRoles({Group.ADMIN,Group.USER})
@LoginConfig(authMethod = "MP-JWT",realmName = "template")
public class SecurityConfig {
    
}