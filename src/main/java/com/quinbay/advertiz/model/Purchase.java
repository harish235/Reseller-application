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
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="pid")
    Integer pid;

    @Column(name = "advertisementid")
    Integer advertisementid;

    @Column(name= "buyerid")
    Integer buyerid;

    @Column(name = "sellerid")
    Integer selllerid;

    @Column(name = "finalprice")
    int finalprice;


    @Column(name="date")
    @CreationTimestamp
    LocalDateTime date;

    @Column(name="status")
    @Type(type = "org.hibernate.type.BooleanType")
    Boolean status;
}
