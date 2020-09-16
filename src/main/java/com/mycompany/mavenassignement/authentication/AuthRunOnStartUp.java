/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.authentication;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author mikaelhei
 */
@Singleton
@Startup
public class AuthRunOnStartUp {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    public void init() {
        long groups = (long) em.createQuery("SELECT count(g.name) from agroup g").getSingleResult();
        if(groups == 0){
            em.persist(new Group(Group.USER));
            em.persist(new Group(Group.ADMIN));
        }
    }
}