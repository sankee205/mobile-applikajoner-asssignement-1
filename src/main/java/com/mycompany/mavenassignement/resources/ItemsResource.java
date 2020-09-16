/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.resources;

import com.mycompany.mavenassignement.Item;
import com.mycompany.mavenassignement.services.ItemService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author sanderkeedklang
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("items")
@Stateless
public class ItemsResource {
    @Inject
    ItemService itemService;
    
    @GET
    public List<Item> getItems(){
        return itemService.getAllItems();
    }
    
}
