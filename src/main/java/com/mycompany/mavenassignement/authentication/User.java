/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.authentication;
import com.mycompany.mavenassignement.authentication.Group;
import static com.mycompany.mavenassignement.authentication.User.FIND_ALL_USERS;
import static com.mycompany.mavenassignement.authentication.User.FIND_USER_BY_IDS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Version;


/**
 *
 * @author sanderkeedklang
 */

@Entity
@Table(name = "users",  schema = "public")
@NamedQuery(name = FIND_ALL_USERS, query = "select u from User u order by u.firstname")
@NamedQuery(name = FIND_USER_BY_IDS, query = "select u from User u where u.username in :ids")
public class User implements Serializable{
    public static final String FIND_USER_BY_IDS = "User.findUserByIds";
    public static final String FIND_ALL_USERS = "User.findAllUsers";
    public enum State {
        ACTIVE, INACTIVE
    }
    
    private static final long serialVersionUID = 1L;
    
    @Version
    int version;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date created;

    @Enumerated(EnumType.STRING)
    State currentState = State.ACTIVE;
    
    @Column(name = "fname", nullable = false)
    private String firstname;
    
    @Column(name = "lname", nullable = false)
    private String lastname;
    
    @Id
    @Column(name = "uid", nullable = false)
    private String username;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "password", nullable = false)
    private String password;
   
    @ManyToMany
    @JoinTable(name="AUSERGROUP",
            joinColumns = @JoinColumn(name="uid", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name="name",referencedColumnName = "name"))
    List<Group> groups;

    public User()
    {
    }
    

    public User(String firstname, String lastname, String username,String email, String password)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /*public String getId()
    {
        return id;
    }*/

    public String getName()
    {
        String fullname = firstname + " " + lastname;
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /*public void setId(String id) {
        this.id = id;
    }*/


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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "auser_properties", joinColumns=@JoinColumn(name="uid"))
    @MapKeyColumn(name="key")
    @Column(name = "value")
    Map<String,String> properties = new HashMap<>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }
    
    
    
    
}
