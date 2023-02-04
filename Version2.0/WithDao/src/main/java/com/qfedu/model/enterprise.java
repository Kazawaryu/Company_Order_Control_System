package com.qfedu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class enterprise {
    private int id;
    private String name;
    private String country;
    private String city;
    private String supply_center;
    private String industry;
}
