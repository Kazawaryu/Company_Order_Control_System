package com.example.cs307_proj2_final;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    String type;

    /**
     * Staff_item
     * Totally 8 column needed
     */
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

    public StringProperty salesman_typeProperty() {
        return salesman_type;
    }

    private StringProperty salesman_id;
    private StringProperty salesman_number;
    private StringProperty salesman_name;
    private StringProperty salesman_gender;
    private StringProperty salesman_age;
    private StringProperty salesman_phone;
    private StringProperty salesman_supply_id;
    private StringProperty salesman_type;

    public void creat_salesman(String id, String name, String age, String gender, String number, String supply, String phone, String type) {
        this.salesman_id = new SimpleStringProperty(id);
        this.salesman_number = new SimpleStringProperty(number);
        this.salesman_name = new SimpleStringProperty(name);
        this.salesman_gender = new SimpleStringProperty(gender);
        this.salesman_age = new SimpleStringProperty(age);
        this.salesman_phone = new SimpleStringProperty(phone);
        this.salesman_supply_id = new SimpleStringProperty(supply);
        this.salesman_type = new SimpleStringProperty(type);
        this.type = "staff";
    }

    /**
     * Order_item
     * Totally 11 column needed
     */

    public StringProperty order_idProperty() {
        return order_id;
    }

    public StringProperty order_contract_numberProperty() {
        return order_contract_number;
    }

    public StringProperty order_product_modelProperty() {
        return order_product_model;
    }

    public StringProperty order_salesman_numberProperty() {
        return order_salesman_number;
    }


    public StringProperty order_estimated_delivery_dateProperty() {
        return order_estimated_delivery_date;
    }

    public StringProperty order_lodgement_dateProperty() {
        return order_lodgement_date;
    }


    public StringProperty order_enterpriseProperty() {
        return order_enterprise;
    }


    public StringProperty order_quantityProperty() {
        return order_quantity;
    }


    public StringProperty order_manager_numberProperty() {
        return order_manager_number;
    }


    public StringProperty order_contract_dateProperty() {
        return order_contract_date;
    }


    public StringProperty order_typeProperty() {
        return order_type;
    }

    private StringProperty order_id;
    private StringProperty order_contract_number;
    private StringProperty order_enterprise;
    private StringProperty order_product_model;
    private StringProperty order_quantity;
    private StringProperty order_manager_number;
    private StringProperty order_contract_date;
    private StringProperty order_estimated_delivery_date;
    private StringProperty order_lodgement_date;
    private StringProperty order_salesman_number;
    private StringProperty order_type;


    public void creat_order(String id, String contract_number, String enterprise, String product_mode, String quantity, String manager,
                            String contract_date, String estimated, String lodgement, String salesman, String type) {
        this.order_id = new SimpleStringProperty(id);
        this.order_contract_number = new SimpleStringProperty(contract_number);
        this.order_enterprise = new SimpleStringProperty(enterprise);
        this.order_product_model = new SimpleStringProperty(product_mode);
        this.order_quantity = new SimpleStringProperty(quantity);
        this.order_manager_number = new SimpleStringProperty(manager);
        this.order_contract_date = new SimpleStringProperty(contract_date);
        this.order_estimated_delivery_date = new SimpleStringProperty(estimated);
        this.order_lodgement_date = new SimpleStringProperty(lodgement);
        this.order_salesman_number = new SimpleStringProperty(salesman);
        this.order_type = new SimpleStringProperty(type);
        this.type = "order";
    }

    /**
     * Model_item
     * Totally 11 column needed
     */


    public StringProperty model_idProperty() {
        return model_id;
    }

    public StringProperty model_numberProperty() {
        return model_number;
    }


    public StringProperty model_modelProperty() {
        return model_model;
    }


    public StringProperty model_nameProperty() {
        return model_name;
    }


    public StringProperty model_unit_priceProperty() {
        return model_unit_price;
    }

    private StringProperty model_id;
    private StringProperty model_number;
    private StringProperty model_model;
    private StringProperty model_name;
    private StringProperty model_unit_price;

    public void creat_model(String id, String number, String model, String name, String price) {
        this.model_id = new SimpleStringProperty(id);
        this.model_number = new SimpleStringProperty(number);
        this.model_model = new SimpleStringProperty(model);
        this.model_name = new SimpleStringProperty(name);
        this.model_unit_price = new SimpleStringProperty(price);
        this.type = "model";

    }


    public StringProperty countryProperty() {
        return country;
    }

    public StringProperty aveProperty() {
        return ave;
    }

    private StringProperty country;
    private StringProperty ave;

    public void create_API11(String country, String ave) {
        this.country = new SimpleStringProperty(country);
        this.ave = new SimpleStringProperty(ave);
        type = "API11";
    }


    public StringProperty aProperty() {
        return a;
    }


    public StringProperty bProperty() {
        return b;
    }


    public StringProperty cProperty() {
        return c;
    }


    public StringProperty dProperty() {
        return d;
    }


    public StringProperty eProperty() {
        return e;
    }

    private StringProperty a;
    private StringProperty b;
    private StringProperty c;
    private StringProperty d;
    private StringProperty e;

    public void create_API12(String a, String b, String c, String d, String e) {
        this.a = new SimpleStringProperty(a);
        this.b = new SimpleStringProperty(b);
        this.c = new SimpleStringProperty(c);
        this.d = new SimpleStringProperty(d);
        this.e = new SimpleStringProperty(e);
    }

    public StringProperty AProperty() {
        return A;
    }

    public StringProperty BProperty() {
        return B;
    }

    public StringProperty CProperty() {
        return C;
    }

    public StringProperty DProperty() {
        return D;
    }

    public StringProperty EProperty() {
        return E;
    }

    public StringProperty FProperty() {
        return F;
    }

    private StringProperty A, B, C, D, E, F;

    public void create_API13(String a, String b, String c, String d, String e, String f) {
        this.A = new SimpleStringProperty(a);
        this.B = new SimpleStringProperty(b);
        this.C = new SimpleStringProperty(c);
        this.D = new SimpleStringProperty(d);
        this.E = new SimpleStringProperty(e);
        this.F = new SimpleStringProperty(f);

    }


    public StringProperty product_idProperty() {
        return product_id;
    }

    public StringProperty product_centerProperty() {
        return product_center;
    }

    public StringProperty product_modelProperty() {
        return product_model;
    }

    public StringProperty product_staffProperty() {
        return product_staff;
    }

    public StringProperty product_dateProperty() {
        return product_date;
    }

    public StringProperty product_priceProperty() {
        return product_price;
    }

    public StringProperty product_quantityProperty() {
        return product_quantity;
    }

    private StringProperty product_id;
    private StringProperty product_center;
    private StringProperty product_model;
    private StringProperty product_staff;
    private StringProperty product_date;
    private StringProperty product_price;
    private StringProperty product_quantity;

    public void create_product(String id, String center, String model, String staff, String data, String price, String quantity) {
        this.product_id = new SimpleStringProperty(id);
        this.product_center = new SimpleStringProperty(center);
        this.product_model = new SimpleStringProperty(model);
        this.product_staff = new SimpleStringProperty(staff);
        this.product_date = new SimpleStringProperty(data);
        this.product_price = new SimpleStringProperty(price);
        this.product_quantity = new SimpleStringProperty(quantity);
    }

}
