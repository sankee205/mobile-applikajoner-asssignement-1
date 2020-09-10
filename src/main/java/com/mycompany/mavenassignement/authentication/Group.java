/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement.authentication;

import com.mycompany.mavenassignement.authentication.User;
import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author sanderkeedklang
 */
@Entity @Table(name = "AGROUP")
@Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(exclude="users")
public class Group implements Serializable {
    public static final String USER = "user";
    public static final String ADMIN = "admin";
    public static final String[] GROUPS = {USER, ADMIN};

    @Id
    String name;

    String project;

    @JsonbTransient
    @ManyToMany
    @JoinTable(name="AUSERGROUP",
            joinColumns = @JoinColumn(name="name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name="id",referencedColumnName = "id"))
    List<User> users;

    public Group(String name) {
        this.name = name;
    }
}

