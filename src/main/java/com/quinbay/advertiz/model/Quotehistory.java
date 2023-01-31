package com.quinbay.advertiz.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quotehistory")
public class Quotehistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="qid")
    Integer qid;

    @Column(name = "advertisementid")
    Integer advertisementid;

    @Column(name= "buyerid")
    Integer buyerid;

    @Column(name = "sellerid")
    Integer selllerid;

    @Column(name = "price")
    int price;


    @Column(name="date")
    @CreationTimestamp
    LocalDateTime date;
}
