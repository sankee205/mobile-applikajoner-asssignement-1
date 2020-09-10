/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.resources;

import com.mycompany.mavenassignement.Item;
import com.mycompany.mavenassignement.Photo;
import com.mycompany.mavenassignement.authentication.User;
import com.mycompany.mavenassignement.services.FantService;
import com.mycompany.mavenassignement.services.UserService;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author sanderkeedklang
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("fant")
public class FantServiceResource {
    FantService fantservice = new FantService();
    UserService userservice = new UserService();
    
    
    @GET
    public List<Item> getItem(){
        List<Item> itemsList = fantservice.getItems();
        return itemsList;
    }
    
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Item addItem(JsonArray array){
        // handles the json response of item
        String itemresponse = array.get(0).toString();
        String[] response = itemresponse.split(",", 3);
        String[] line1 = response[0].split(":");
        String[] line2 = response[1].split(":");
        String[] line3 = response[2].split(":");


        String name = line1[1];
        String category = line2[1];
        String priceString = line3[1].trim().replace("}", "");
        int price = Integer.parseInt(priceString);
        
        //creates new item
        Item item = new Item(name, category, price);
        
              
        
        return fantservice.addItem(item);
       
    }
    
   
}
