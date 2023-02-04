package com.qfedu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class contract {
    private String contract_num;
    private String enterprise;
    private int contract_manager;
    private Date contract_date;
    private Date estimated_delivery_date;
    private Date lodgement_date;
    private String contract_type;
}
