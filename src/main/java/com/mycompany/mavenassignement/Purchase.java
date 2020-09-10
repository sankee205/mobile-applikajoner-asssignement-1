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
@Table(name = "purchase")
public class Purchase implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "purchaseid", unique = true)
    private String purchaseid;
    
    @Column(name = "sellerid", nullable = false)
    private String sellerid;
    
    @Column(name = "buyerid", nullable = false)
    private String buyerid;
    
    @Column(name = "itemid", nullable = false)
    private String itemid;
    
    @Column(name = "purchased", nullable = false)
    private boolean purchased;

    public Purchase(String sellerid, String itemid) {
        this.sellerid = sellerid;
        this.itemid = itemid;
    }

    public Purchase() {
    }

    public String getPurchaseid() {
        return purchaseid;
    }

    public void setPurchaseid(String purchaseid) {
        this.purchaseid = purchaseid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

     
       
   
}
