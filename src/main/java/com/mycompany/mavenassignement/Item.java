/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenassignement;

import com.mycompany.mavenassignement.authentication.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author sanderkeedklang
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String title;
    private String description;
    private BigDecimal price;

    @OneToMany
    private List<Photo> itemImages;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User itemOwner;

    @ManyToOne
    private User itemBuyer;


}
