package com.qfedu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@ToString
public class orders {
    private String contract_num;
    private String enterprise;
    private String product_model;
    private int quantity;
    private int contract_manager;
    private Date contract_date;
    private Date estimated_delivery_date;
    private Date lodgement_date;

    public orders(String contract_num, String enterprise, String product_model, int quantity, int contract_manager, Date contract_date, Date estimated_delivery_date, Date lodgement_date, int salesman_num, String contract_type) {
        this.contract_num = contract_num;
        this.enterprise = enterprise;
        this.product_model = product_model;
        this.quantity = quantity;
        this.contract_manager = contract_manager;
        this.contract_date = contract_date;
        this.estimated_delivery_date = estimated_delivery_date;
        this.lodgement_date = lodgement_date;
        this.salesman_num = salesman_num;
        this.contract_type = contract_type;
    }

    private int salesman_num;
    private String contract_type;
    private int id;//serial
}
