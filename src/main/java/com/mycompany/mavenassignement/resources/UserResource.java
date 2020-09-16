/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.resources;

import com.mycompany.mavenassignement.authentication.User;
import com.mycompany.mavenassignement.services.UserService;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author sanderkeedklang
 */
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
public class UserResource {
    
    UserService userService = new UserService();
    
    @GET
    public List<User> getUsers(){
        List<User> userlist = userService.getAllUsers();
        return userlist;
    }
    
    
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test(){
        return "test";
    }
    
    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User userId(@PathParam("userId") long userId){
        return userService.getUser(userId);
    }
}
