package com.quinbay.advertiz.model;

//
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subcategory")
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "categoryid")
    int categoryid;
}
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@Table(name = "subcategories")
//public class Subcategory implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String name;
//
////    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "category", nullable = false)
//    private Category category;
//
//    public Subcategory() {
//    }
//
//    public Subcategory(String name, Category category) {
//        this.name = name;
//        this.category = category;
//    }
//}

