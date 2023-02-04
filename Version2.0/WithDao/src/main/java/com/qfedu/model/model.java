package com.qfedu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class model {
    private int id;
    private String number;
    private String model;
    private String name;
    private int unit_price;
}
