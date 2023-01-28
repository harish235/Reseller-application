package com.quinbay.advertiz.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adpost {
    int sellerid;
    String subcategoryname;
    String title;
    String description;
    int price;
    int minimumprice;
}
