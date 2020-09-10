/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author sanderkeedklang
 */
@Entity
@Table(name = "items")
public class Item implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", unique = true)
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "category", nullable = false)
    private String category;
    
    @Column(name = "price", nullable = false)
    private int price;
    
    

    public Item() {
    }

    public Item(String name, String category, int price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }
    
   
    public void setId(String id){
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }
   
    
    
}
