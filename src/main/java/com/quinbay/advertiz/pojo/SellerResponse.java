package com.quinbay.advertiz.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerResponse {
    Integer adid;
    String subcategoryname;
    String title;
    String buyername;
    String selleremail;
    Integer finalprice;
    LocalDateTime date;
}
