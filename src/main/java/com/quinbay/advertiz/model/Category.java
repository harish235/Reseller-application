package com.quinbay.advertiz.model;

//import lombok.*;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
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
//}

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;

    @OneToMany(targetEntity = Subcategory.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cs_fk", referencedColumnName = "id")
    List<Subcategory> subcategory;
}
