package com.quinbay.advertiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "advertisement")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="adid")
    int adid;

    @Column(name = "sellerid")
    int sellerid;

    @Column(name= "subcategoryid")
    int subcategoryid;

    @Column(name = "categoryid")
    int categoryid;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "price")
    int price;

    @Column(name = "minimumprice")
    int minimumprice;

    @Column(name="postedDate")
    @CreationTimestamp
    LocalDateTime posetdDate;

    @Column(name="status")
    @Type(type = "org.hibernate.type.BooleanType")
    Boolean status;

    @Column(name = "viewcount")
    int viewcount;
}
