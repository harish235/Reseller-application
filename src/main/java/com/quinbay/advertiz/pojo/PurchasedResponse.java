package com.quinbay.advertiz.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedResponse {

    Integer pid;
    Integer advertisementid;
    String adTitle;
    String buyerName;
    String selllerEmail;
    int finalprice;
    LocalDateTime date;
}
