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
    int qid;

    @Column(name = "advertisementid")
    int advertisementid;

    @Column(name= "buyerid")
    int buyerid;

    @Column(name = "sellerid")
    int selllerid;

    @Column(name = "price")
    int price;


    @Column(name="date")
    @CreationTimestamp
    LocalDateTime date;
}
