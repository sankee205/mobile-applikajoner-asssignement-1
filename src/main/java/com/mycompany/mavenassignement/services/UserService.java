/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.services;

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
import javax.persistence.TypedQuery;





/**
 *
 * @author sanderkeedklang
 */
public class UserService {
    
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("com.mycompany_mavenassignement_war_1.0-SNAPSHOTPU");    
        
    public UserService(){

    }
    
    public List<User> getAllUsers(){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "from User";


        TypedQuery<User> tq = em.createQuery(query, User.class);
        List<User> users = null;
        
        try{
            users = tq.getResultList();
            users.forEach(user -> System.out.println(user.getName()));
            
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return users;
    }
    
    public User addUser(User user){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction et = null;
        try{
            et = em.getTransaction();
            et.begin();
            em.persist(user);
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
        return user;
    }
    
    public User getUser(long id){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.id = :userId";
        
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("userId", id);
        User user = null;
        try{
            user = tq.getSingleResult();
            System.out.println(user.getName());
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return user;
    }
    public User getUserByUsernameAndPassword(String username, String password){
        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        String query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password";
        
        TypedQuery<User> tq = em.createQuery(query, User.class);
        tq.setParameter("username", username);
        tq.setParameter("password", password);
        User user = null;
        try{
            user = tq.getSingleResult();
            System.out.println(user.getName());
        }
        catch(NoResultException e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return user;
    }
}
