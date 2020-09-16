/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.services;

import com.mycompany.mavenassignement.Purchase;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author sanderkeedklang
 */
public class PurchaseService {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("com.mycompany_mavenassignement_war_1.0-SNAPSHOTPU");    
        
    public PurchaseService(){
       

    }
    
    public List<Purchase> getAllPurchase(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "from Purchase";


        TypedQuery<Purchase> tq = em.createQuery(query, Purchase.class);
        List<Purchase> purchases = null;
        
        try{
            purchases = tq.getResultList();
            purchases.forEach(purchase -> System.out.println(purchase.getPurchaseid()));
            
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return purchases;
    }
    
    public Purchase addPurchase(Purchase purchase){
        purchase.setPurchased(false);
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(purchase);
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
        return purchase;
    }
    
    public Purchase getPurchase(String itemid){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM Purchase u WHERE u.itemid = : itemid";
        
        TypedQuery<Purchase> tq = em.createQuery(query, Purchase.class);
        tq.setParameter("itemid", itemid);
        Purchase purchase = null;
        try{
            purchase = tq.getSingleResult();
            System.out.println(purchase.getPurchaseid());
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return purchase;
    }
    
    public Purchase PurchaseItem(String purchaseid, String userid){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Purchase purchase = null;
        try{
            et = em.getTransaction();
            et.begin();
            purchase = em.find(Purchase.class, purchaseid);
            purchase.setBuyerid(userid);
            purchase.setPurchased(true);
            
            em.persist(purchase);
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
        return purchase;
    }
    
    public Purchase DeletePurchase(String purchaseid){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        Purchase purchase = null;
        try{
            et = em.getTransaction();
            et.begin();
            
            purchase = em.find(Purchase.class, purchaseid);
            em.remove(purchase);
            
            em.persist(purchase);
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
        return purchase;
    }
}
