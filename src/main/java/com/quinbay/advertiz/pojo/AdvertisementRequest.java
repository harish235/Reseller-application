package com.quinbay.advertiz.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {
    Integer adid;
    Integer sellerid;
    Integer subcategoryid;
    Integer categoryid;
    String title;
    String description;
    Integer price;
    Integer minimumprice;
    LocalDateTime postedDate;
    Boolean status;
    Integer viewcount;
    String imgpath;
    Integer topQuotedAmount;
}
