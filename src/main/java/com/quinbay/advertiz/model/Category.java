package com.quinbay.advertiz.model;


//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@Table(name = "category")
//public class Category implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    @Column(unique = true)
//    private String name;
//
//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<Subcategory> subcategories;
//
//    public Category() {
//    }
//
//    public Category(String name) {
//        this.name = name;
//    }
//
//    // getters and setters, equals(), toString() .... (omitted for brevity)
//}


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;

    // getters and setters, equals(), toString() .... (omitted for brevity)
}
