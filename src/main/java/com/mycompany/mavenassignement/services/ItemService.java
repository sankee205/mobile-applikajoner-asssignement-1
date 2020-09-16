/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.services;


import com.mycompany.mavenassignement.Item;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sanderkeedklang
 */
@Stateless
public class ItemService {

    @PersistenceContext
    EntityManager em;
    
    public List<Item> getAllItems(){
        String query = "select i from Item i";

        TypedQuery<Item> tq = em.createQuery(query, Item.class);
        List<Item> items = null;
        
        try{
            items = tq.getResultList();
            items.forEach(item -> System.out.println(item.getName()));
            
        }
        catch(NoResultException e){
            e.printStackTrace();
        }

        return items;
    }
    
    public Item addItem(Item item){
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
        
        return item;
    }
    
    public Item DeleteItem(String itemid){
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
