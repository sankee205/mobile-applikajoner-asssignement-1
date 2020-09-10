/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.services;

import com.mycompany.mavenassignement.Item;
import com.mycompany.mavenassignement.Purchase;
import com.mycompany.mavenassignement.authentication.AuthenticationService;
import com.mycompany.mavenassignement.authentication.User;


import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import org.hibernate.jpa.AvailableSettings;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author sanderkeedklang
 */
public class FantService {
    ItemService itemservice = new ItemService();
    PurchaseService purchaseservice = new PurchaseService();
    UserService userservice = new UserService();
    AuthenticationService as = new AuthenticationService();
    
    /**
* Public method that returns items with photos sold in the shop
*/
    
    
public FantService() {
    }

    public List<Item> getItems() {
        return itemservice.getAllItems();
    }

/**
* A registered user may purchase an Item. An email will be sent to the
* seller if the purchase is successful
*
* @param itemid unique id for item
* @return result of purchase request
*/
public Purchase purchase(String itemid, String userid) {
    Purchase purchase = purchaseservice.getPurchase(itemid);
    return purchaseservice.PurchaseItem(purchase.getPurchaseid(),userid);
}
 /**
 * A registered user may remove an item and associated photos owned by the
 * calling user. An user with administrator privileges may remove any item
 * and associated photos.
 *
 * @param itemid unique id for item to be deleted
 * @return result of delete request
 */
public Item delete(String itemid) {
    Purchase purchase = purchaseservice.getPurchase(itemid);
    
    return itemservice.DeleteItem(itemid);
}
/**
 * A registered user may add an item and photo(s) to Fant.
 *
 * @param title the title of Item
 * @param description the description of Item
 * @param price the price of Item
 * @param photos one or more photos associated with Item
 * @return result of the request. If successful, the request will include
 * the new unique ids of the Item and associated Photos
 */
 public Item addItem(Item item){
    itemservice.addItem(item);
    User user = getCurrentUser();
    
    String userid = user.getId();
    Purchase purchase = new Purchase(userid, item.getId());
    purchaseservice.addPurchase(purchase);
    return item;
 }
 /**
 * Streams an image to the browser (the actual compressed pixels). The image
 * will be scaled to the appropriate width if the width parameter is provided.
 * This is a public method available to all callers.
 *
 * @param name the filename of the image
 * @param width the required scaled with of the image
 *
 * @return the image in original format or in jpeg if scaled
 */
 public Response getPhoto(String name, int width) {
     return null;
 }
 
 
 private User getCurrentUser(){
     return as.getCurrentUser();
 }
}
