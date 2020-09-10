/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.authentication;
import com.mycompany.mavenassignement.authentication.Group;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 *
 * @author sanderkeedklang
 */

@Entity
@Table(name = "users")
public class User implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id", unique = true)
    private String id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
   
    @ManyToMany
    @JoinTable(name="AUSERGROUP",
            joinColumns = @JoinColumn(name="userid", referencedColumnName = "userid"),
            inverseJoinColumns = @JoinColumn(name="name",referencedColumnName = "name"))
    List<Group> groups;

    public User()
    {
    }
    

    public User(String name, String username, String password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Group> getGroups() {
        if(groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }
    
    
    
}
