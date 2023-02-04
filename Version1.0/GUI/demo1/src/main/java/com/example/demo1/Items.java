package com.example.demo1;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Items {
    public StringProperty order_idProperty() {
        return order_id;
    }

    public StringProperty order_contract_numberProperty() {
        return order_contract_number;
    }

    public StringProperty order_product_codeProperty() {
        return order_product_code;
    }

    public StringProperty order_salesman_numberProperty() {
        return order_salesman_number;
    }

    public StringProperty product_numProperty() {
        return product_num;
    }

    public StringProperty estimated_delivery_dateProperty() {
        return estimated_delivery_date;
    }

    public StringProperty lodgement_dateProperty() {
        return lodgement_date;
    }

    private StringProperty order_id;
    private StringProperty order_contract_number;
    private StringProperty order_product_code;
    private StringProperty order_salesman_number;
    private StringProperty product_num;
    private StringProperty estimated_delivery_date;
    private StringProperty lodgement_date;

    public void creat_order(String id, String contract_number, String product_code, String salesman_number, String product_num, String estimated_delivery_date, String lodgement_date) {
        this.order_id = new SimpleStringProperty(id);
        this.order_contract_number = new SimpleStringProperty(contract_number);
        this.order_product_code = new SimpleStringProperty(product_code);
        this.order_salesman_number = new SimpleStringProperty(salesman_number);
        this.product_num = new SimpleStringProperty(product_num);
        this.estimated_delivery_date = new SimpleStringProperty(estimated_delivery_date);
        this.lodgement_date = new SimpleStringProperty(lodgement_date);
    }


    public StringProperty product_idProperty() {
        return product_id;
    }

    public StringProperty product_codeProperty() {
        return product_code;
    }

    public StringProperty product_modelProperty() {
        return product_model;
    }

    public StringProperty product_nameProperty() {
        return product_name;
    }

    public StringProperty priceProperty() {
        return price;
    }

    private StringProperty product_id;
    private StringProperty product_code;
    private StringProperty product_model;
    private StringProperty product_name;
    private StringProperty price;

    public void creat_product(String id, String code, String model, String name, String price) {
        this.product_id = new SimpleStringProperty(id);
        this.product_code = new SimpleStringProperty(code);
        this.product_model = new SimpleStringProperty(model);
        this.product_name = new SimpleStringProperty(name);
        this.price = new SimpleStringProperty(price);
    }


    public StringProperty customer_idProperty() {
        return customer_id;
    }

    public StringProperty client_enterprise_nameProperty() {
        return client_enterprise_name;
    }

    public StringProperty customer_countryProperty() {
        return customer_country;
    }

    public StringProperty customer_cityProperty() {
        return customer_city;
    }

    public StringProperty customer_industryProperty() {
        return customer_industry;
    }

    private StringProperty customer_id;
    private StringProperty client_enterprise_name;
    private StringProperty customer_country;
    private StringProperty customer_city;
    private StringProperty customer_industry;

    public void creat_customer(String id, String client, String country, String city, String industry) {
        this.customer_id = new SimpleStringProperty(id);
        this.client_enterprise_name = new SimpleStringProperty(client);
        this.customer_country = new SimpleStringProperty(country);
        this.customer_city = new SimpleStringProperty(city);
        this.customer_industry = new SimpleStringProperty(industry);
    }


    public StringProperty contract_idProperty() {
        return contract_id;
    }

    public StringProperty contract_numberProperty() {
        return contract_number;
    }

    public StringProperty contract_dataProperty() {
        return contract_data;
    }

    public StringProperty contract_customer_idProperty() {
        return contract_customer_id;
    }

    private StringProperty contract_id;
    private StringProperty contract_number;
    private StringProperty contract_data;
    private StringProperty contract_customer_id;

    public void creat_contract(String id, String number, String data, String customer) {
        this.contract_id = new SimpleStringProperty(id);
        this.contract_number = new SimpleStringProperty(number);
        this.contract_data = new SimpleStringProperty(data);
        this.contract_customer_id = new SimpleStringProperty(customer);
    }


    public StringProperty relation_industryProperty() {
        return relation_industry;
    }

    public StringProperty relation_supplyProperty() {
        return relation_supply;
    }

    public StringProperty relation_managerProperty() {
        return relation_manager;
    }

    public StringProperty relation_supply_idProperty() {
        return relation_supply_id;
    }

    private StringProperty relation_industry;
    private StringProperty relation_supply;
    private StringProperty relation_manager;
    private StringProperty relation_supply_id;

    public void creat_relation(String industry, String supply, String manager, String id) {
        this.relation_industry = new SimpleStringProperty(industry);
        this.relation_supply = new SimpleStringProperty(supply);
        this.relation_manager = new SimpleStringProperty(manager);
        this.relation_supply_id = new SimpleStringProperty(id);
    }

    public StringProperty salesman_idProperty() {
        return salesman_id;
    }

    public StringProperty salesman_numberProperty() {
        return salesman_number;
    }

    public StringProperty salesman_nameProperty() {
        return salesman_name;
    }

    public StringProperty salesman_genderProperty() {
        return salesman_gender;
    }

    public StringProperty salesman_ageProperty() {
        return salesman_age;
    }

    public StringProperty salesman_phoneProperty() {
        return salesman_phone;
    }

    public StringProperty salesman_supply_idProperty() {
        return salesman_supply_id;
    }

    private StringProperty salesman_id;
    private StringProperty salesman_number;
    private StringProperty salesman_name;
    private StringProperty salesman_gender;
    private StringProperty salesman_age;
    private StringProperty salesman_phone;
    private StringProperty salesman_supply_id;

    public void creat_salesman(String id, String number, String name, String gender, String age, String phone, String supply) {
        this.salesman_id = new SimpleStringProperty(id);
        this.salesman_number = new SimpleStringProperty(number);
        this.salesman_name = new SimpleStringProperty(name);
        this.salesman_gender = new SimpleStringProperty(gender);
        this.salesman_age = new SimpleStringProperty(age);
        this.salesman_phone = new SimpleStringProperty(phone);
        this.salesman_supply_id = new SimpleStringProperty(supply);
    }


    public StringProperty supply_idProperty() {
        return supply_id;
    }

    public StringProperty supply_nameProperty() {
        return supply_name;
    }

    public StringProperty supply_managerProperty() {
        return supply_manager;
    }

    private StringProperty supply_id;
    private StringProperty supply_name;
    private StringProperty supply_manager;

    public void creat_supply(String id, String name, String manager) {
        this.supply_id = new SimpleStringProperty(id);
        this.supply_name = new SimpleStringProperty(name);
        this.supply_manager = new SimpleStringProperty(manager);
    }

}