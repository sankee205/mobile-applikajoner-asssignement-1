/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement;


import com.mycompany.mavenassignement.authentication.Group;
import com.mycompany.mavenassignement.services.FantService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Singleton
@Startup
public class RunOnStartup {
  
    @PersistenceContext
    EntityManager em;
    
    @Inject
    FantService fantservice;
    
    @PostConstruct
    public void init() {
        long groups = (long) em.createQuery("SELECT count(g.name) from agroup g").getSingleResult();
        if(groups == 0) {
            em.persist(new Group(Group.USER));
            em.persist(new Group(Group.ADMIN));
        }
        
        long users = (long)em.createQuery("SELECT count(u.userid) from User u").getSingleResult();
        if(users == 0){
            fantservice.getItems();
        }
    }
}