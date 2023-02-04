package com.qfedu.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString

public class product {
    private int id;
    private String supply_center;
    private String product_model;
    private int supply_staff;
    private Date date;
    private int purchase_price;
    private int quantity;

    public product(int id, String supply_center, String product_model, int supply_staff, Date date, int purchase_price, int quantity) {
        this.id = id;
        this.supply_center = supply_center;
        this.product_model = product_model;
        this.supply_staff = supply_staff;
        this.date = date;
        this.purchase_price = purchase_price;
        this.quantity = quantity;
    }
}
