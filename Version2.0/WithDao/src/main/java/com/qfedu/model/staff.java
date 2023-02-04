package com.qfedu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class staff {
    private int id;
    private String name;
    private int age;
    private String gender;
    private int number;
    private String supply_center;
    private long mobile_number;
    private String type;
}
