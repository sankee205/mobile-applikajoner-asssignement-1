/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.services;


import com.mycompany.mavenassignement.Item;
import com.mycompany.mavenassignement.authentication.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sanderkeedklang
 */
public class ItemService {
     
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("com.mycompany_mavenassignement_war_1.0-SNAPSHOTPU");
    public ItemService(){
        

    }
    
    public List<Item> getAllItems(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();

        String query = "from Item";


        TypedQuery<Item> tq = em.createQuery(query, Item.class);
        List<Item> items = null;
        
        try{
            items = tq.getResultList();
            items.forEach(item -> System.out.println(item.getName()));
            
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return items;
    }
    
    public Item addItem(Item item){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(item);
            et.commit();
            
        }
        catch(Exception e){
            if(et != null){
                et.rollback();
            }
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return item;
    }
    
    public Item getItem(String itemId){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT i FROM Item i WHERE i.id = :itemId";
        
        TypedQuery<Item> tq = em.createQuery(query, Item.class);
        tq.setParameter("id", itemId);
        Item item = null;
        try{
            item = tq.getSingleResult();
            System.out.println(item.getName());
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return item;
    }
    
    public Item DeleteItem(String itemid){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Item item = null;
        try{
            et = em.getTransaction();
            et.begin();
            item = em.find(Item.class, itemid);
            em.remove(item);
            
            em.persist(item);
            et.commit();
            
        }
        catch(Exception e){
            if(et != null){
                et.rollback();
            }
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return item;
    }
     
    
}
