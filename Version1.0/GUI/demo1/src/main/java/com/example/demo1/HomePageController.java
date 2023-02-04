package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public class HomePageController {
    int rank = -1;
    int opLength = 0;
    int operating_time = 0;
    static String latTable = null;
    static String latButton = null;
    static String latId = null;
    static Connection con;
    static ResultSet res;
    static ArrayList<Integer> latRes = new ArrayList<>();
    static PreparedStatement stmt = null;
    static boolean verbose = false;

    private static void openDB(String host, String dbname, String user, String pwd) {
        try {
            //
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        String url = "jdbc:postgresql://" + host + "/" + dbname;
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pwd);
        try {
            con = DriverManager.getConnection(url, props);
            if (verbose) {
                System.out.println("Successfully connected to the database "
                        + dbname + " as " + user);
            }
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }

    public void connect() {
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "Proj_01");
        Properties prop = new Properties(defprop);

        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));

    }

    @FXML
    private MenuItem Order;

    @FXML
    private MenuItem Salesman;

    @FXML
    private MenuItem Supplycenter;

    @FXML
    private TextField keywords;

    @FXML
    private MenuItem Customer;

    @FXML
    private MenuButton tablemune;

    @FXML
    private MenuItem Product;

    @FXML
    private MenuButton waymenu;

    @FXML
    private MenuItem Contract;

    @FXML
    private MenuItem relation;

    @FXML
    private MenuItem Scope;

    @FXML
    private MenuItem Blurry;

    @FXML
    private MenuItem Accurate;

    @FXML
    private TextField rowmenu;


    @FXML
    private TableColumn<Items, String> C1 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C2 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C3 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C4 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C5 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C6 = new TableColumn<>();

    @FXML
    private TableColumn<Items, String> C7 = new TableColumn<>();

    @FXML
    private TableView<Items> TableViewer = new TableView<>();


    @FXML
    void chooseContract(ActionEvent event) {
        tablemune.setText(Contract.getText());
    }

    @FXML
    void chooseCustomer(ActionEvent event) {
        tablemune.setText(Customer.getText());
    }

    @FXML
    void chooseOrder(ActionEvent event) {
        tablemune.setText(Order.getText());
    }

    @FXML
    void chooseProduct(ActionEvent event) {
        tablemune.setText(Product.getText());
    }

    @FXML
    void chooseSalesman(ActionEvent event) {
        tablemune.setText(Salesman.getText());
    }

    @FXML
    void chooseSupplycenter(ActionEvent event) {
        tablemune.setText(Supplycenter.getText());
    }

    @FXML
    void chooseRelation(ActionEvent event) {
        tablemune.setText(relation.getText());
    }

    @FXML
    void chooseAccurate(ActionEvent event) {
        waymenu.setText(Accurate.getText());
    }

    @FXML
    void chooseBlurry(ActionEvent event) {
        waymenu.setText(Blurry.getText());
    }

    @FXML
    void chooseScope(ActionEvent event) {
        waymenu.setText(Scope.getText());
    }


    public static ObservableList<Items> itemsListCell = FXCollections.observableArrayList();

    @FXML
    private Tab RankText;

    @FXML
    void SELECT(ActionEvent event) throws IOException {
        if (rank == -1) {
            File file = new File("rank.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String rank_stm = reader.readLine();
            reader.close();
            file.delete();
            if (rank_stm.equals("Super login")) {
                rank = 1;
                RankText.setText("Super User: Ghosn");
            } else if (rank_stm.equals("Normal login")) {
                rank = 0;
                RankText.setText("Normal User: Yoko");
            }
        }

        connect();
        operating_time++;

        String table = tablemune.getText();
        String row = rowmenu.getText();
        String way = waymenu.getText();
        String words = keywords.getText();

        if (Objects.equals(table, "") | Objects.equals(row, "") | Objects.equals(way, "") | Objects.equals(words, "")) {
            System.out.println("Missing searching information");
        } else {
            try {
                clean_view();
                switch (way) {
                    case "Accurate" -> {
                        words = "'" + words + "'";
                        String sqlstm = "select * from " + table + " where " + row + " = " + words;
                        System.out.println(sqlstm);
                        Statement stms = con.createStatement();
                        res = stms.executeQuery(sqlstm);
                        latRes.clear();
                    }
                    case "Blurry" -> {
                        words = "'" + words + "'";
                        String sqlstm = "select * from " + table + " where " + row + " like " + words;
                        System.out.println(sqlstm);
                        Statement stms = con.createStatement();
                        res = stms.executeQuery(sqlstm);
                        latRes.clear();
                    }
                    case "Scope" -> {
                        String[] tokens = words.split("-");
                        String sqlstm = "select * from " + table + " where " + row + " >= " + tokens[0] + " and " + row + " <= " + tokens[1];
                        System.out.println(sqlstm);
                        Statement stms = con.createStatement();
                        res = stms.executeQuery(sqlstm);
                        latRes.clear();
                    }
                }
                if (res != null) {
                    switch (table) {
                        case "orders" -> view_order_select(res);
                        case "product" -> view_product_select(res);
                        case "customer" -> view_customer_select(res);
                        case "contract" -> view_contract_select(res);
                        case "relation" -> view_relation_select(res);
                        case "salesman" -> view_salesman_select(res);
                        case "supplycenter" -> view_supply_select(res);
                    }

                }
                System.out.println("View data over");
                closeDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    void clean_view() {
        TableViewer.getItems().clear();
        res = null;
        C1.setText("C1");
        C2.setText("C2");
        C3.setText("C3");
        C4.setText("C4");
        C5.setText("C5");
        C6.setText("C6");
        C7.setText("C7");
    }

    void view_order_select(ResultSet res) throws SQLException {
        latTable = "orders";
        opLength = 7;
        latId = "order_id";
        C1.setText("order_id");
        C2.setText("contract_number");
        C3.setText("product_code");
        C4.setText("salesman_number");
        C5.setText("product_num");
        C6.setText("estimated_delivery_date");
        C7.setText("lodgement_date");

        C1.setCellValueFactory(data -> data.getValue().order_idProperty());
        C2.setCellValueFactory(data -> data.getValue().order_contract_numberProperty());
        C3.setCellValueFactory(data -> data.getValue().order_product_codeProperty());
        C4.setCellValueFactory(data -> data.getValue().order_salesman_numberProperty());
        C5.setCellValueFactory(data -> data.getValue().product_numProperty());
        C6.setCellValueFactory(data -> data.getValue().estimated_delivery_dateProperty());
        C7.setCellValueFactory(data -> data.getValue().lodgement_dateProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_order(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7));
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_product_select(ResultSet res) throws SQLException {
        latTable = "product";
        opLength = 5;
        latId = "product_id";
        C1.setText("product_id");
        C2.setText("product_code");
        C3.setText("product_model");
        C4.setText("product_name");
        C5.setText("price");

        C1.setCellValueFactory(data -> data.getValue().product_idProperty());
        C2.setCellValueFactory(data -> data.getValue().product_codeProperty());
        C3.setCellValueFactory(data -> data.getValue().product_modelProperty());
        C4.setCellValueFactory(data -> data.getValue().product_nameProperty());
        C5.setCellValueFactory(data -> data.getValue().priceProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_product(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_customer_select(ResultSet res) throws SQLException {
        latTable = "customer";
        opLength = 5;
        latId = "customer_id";
        C1.setText("customer_id");
        C2.setText("client_enterprise_name");
        C3.setText("country");
        C4.setText("city");
        C5.setText("industry");

        C1.setCellValueFactory(data -> data.getValue().customer_idProperty());
        C2.setCellValueFactory(data -> data.getValue().client_enterprise_nameProperty());
        C3.setCellValueFactory(data -> data.getValue().customer_countryProperty());
        C4.setCellValueFactory(data -> data.getValue().customer_cityProperty());
        C5.setCellValueFactory(data -> data.getValue().customer_industryProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_customer(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_contract_select(ResultSet res) throws SQLException {
        latTable = "contract";
        opLength = 3;
        latId = "contract_id";
        C1.setText("id");
        C2.setText("contract_number");
        C3.setText("contract_date");
        C4.setText("customer_id");

        C1.setCellValueFactory(data -> data.getValue().contract_idProperty());
        C2.setCellValueFactory(data -> data.getValue().contract_numberProperty());
        C3.setCellValueFactory(data -> data.getValue().contract_dataProperty());
        C4.setCellValueFactory(data -> data.getValue().contract_customer_idProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_contract(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)

            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_relation_select(ResultSet res) throws SQLException {
        latTable = "relation";
        opLength = 4;
        latId = "industry";
        C1.setText("industry");
        C2.setText("supply_center");
        C3.setText("manager");
        C4.setText("supply_center_id");

        C1.setCellValueFactory(data -> data.getValue().relation_industryProperty());
        C2.setCellValueFactory(data -> data.getValue().relation_supplyProperty());
        C3.setCellValueFactory(data -> data.getValue().relation_managerProperty());
        C4.setCellValueFactory(data -> data.getValue().relation_supply_idProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_relation(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4)

            );
            itemsListCell.add(add);
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_salesman_select(ResultSet res) throws SQLException {
        latTable = "salesman";
        opLength = 7;
        latId = "salesman_id";
        C1.setText("salesman_id");
        C2.setText("salesman_number");
        C3.setText("salesman_name");
        C4.setText("gender");
        C5.setText("age");
        C6.setText("phone_number");
        C7.setText("supply_center_id");

        C1.setCellValueFactory(data -> data.getValue().salesman_idProperty());
        C2.setCellValueFactory(data -> data.getValue().salesman_numberProperty());
        C3.setCellValueFactory(data -> data.getValue().salesman_nameProperty());
        C4.setCellValueFactory(data -> data.getValue().salesman_genderProperty());
        C5.setCellValueFactory(data -> data.getValue().salesman_ageProperty());
        C6.setCellValueFactory(data -> data.getValue().salesman_phoneProperty());
        C7.setCellValueFactory(data -> data.getValue().salesman_supply_idProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_salesman(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7));
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    void view_supply_select(ResultSet res) throws SQLException {
        latTable = "supplycenter";
        opLength = 3;
        latId = "supply_center_id";
        C1.setText("supply_center_id");
        C2.setText("supply_center");
        C3.setText("manager");

        C1.setCellValueFactory(data -> data.getValue().supply_idProperty());
        C2.setCellValueFactory(data -> data.getValue().supply_nameProperty());
        C3.setCellValueFactory(data -> data.getValue().supply_managerProperty());

        while (res.next()) {
            Items add = new Items();
            add.creat_supply(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3)
            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TableViewer.setItems(itemsListCell);
    }

    @FXML
    private TextField opText;

    @FXML
    void DELETE(ActionEvent event) {
        System.out.println("delete");
        if (rank != -1)
            if (rank == 1) {
                opText.setEditable(true);
                opText.setText("Input sure to delete");
                latButton = "delete";
            } else opText.setText("Insufficient permissions");
    }

    @FXML
    void UPDATE(ActionEvent event) {
        System.out.println("update");
        if (rank != -1)
            if (rank == 1) {
                opText.setEditable(true);
                opText.setText("Input advance information");
                latButton = "update";
            } else opText.setText("Insufficient permissions");
    }

    @FXML
    void INSERT(ActionEvent event) {
        System.out.println("insert");
        if (rank != -1)
            if (rank == 1) {
                opText.setEditable(true);
                opText.setText("Input sure to insert");
                latButton = "insert";
            } else opText.setText("Insufficient permissions");
    }

    @FXML
    void onOPGO(ActionEvent event) throws SQLException {
        if (rank != -1)
            if (opText.isEditable()) {
                if (latButton.equals("delete") && opText.getText().equals("sure")) {
                    connect();
                    System.out.println(latTable + " GO!");
                    PreparedStatement stm = con.prepareStatement("delete from " + latTable + " where " + latId + " = " + "?");
                    for (int i = 0; i < latRes.size(); i++) {
                        stm.setInt(1, latRes.get(i));
                        stm.addBatch();
                        if (i % 500 == 0) {
                            stm.executeBatch();
                            stm.clearBatch();
                        }
                    }
                    stm.executeBatch();
                    con.commit();
                    closeDB();
                } else if (latButton.equals("update")) {
                    String[] words = opText.getText().split(",");
                    System.out.println(words.length + " " + (opLength - 1));
                    if (opLength != 0 && words.length == opLength - 1) {
                        connect();
                        System.out.println(latTable + " GO!");
                        if (latTable.equals("customer")) {
                            System.out.println("update customer");
                            PreparedStatement stm = con.prepareStatement("update customer set client_enterprise_name =? , country = ? , city =? , industry = ? where product_id = ?");
                            for (int i = 0; i < latRes.size(); i++) {
                                stm.setString(1, words[0]);
                                stm.setString(2, words[1]);
                                stm.setString(3, words[2]);
                                stm.setString(4, words[3]);
                                stm.setInt(5, latRes.get(i));
                                stm.addBatch();
                                if (i % 500 == 0) {
                                    stm.executeBatch();
                                    stm.clearBatch();
                                }
                            }
                            stm.executeBatch();
                            con.commit();
                            closeDB();
                        } else if (latTable.equals("orders")) {
                            System.out.println("update orders");
                            PreparedStatement stm = con.prepareStatement("update orders set contract_number = ?,product_code = ?,salesman_number = ?,product_num = ?,estimated_delivery_date=? , lodgement_date = ? where order_id = ?");
                            for (int i = 0; i < latRes.size(); i++) {
                                stm.setInt(1, Integer.parseInt(words[0]));
                                stm.setInt(2, Integer.parseInt(words[1]));
                                stm.setInt(3, Integer.parseInt(words[2]));
                                stm.setInt(4, Integer.parseInt(words[3]));
                                stm.setDate(5, Date.valueOf(words[4]));
                                stm.setDate(6, Date.valueOf(words[5]));
                                stm.setInt(7, latRes.get(i));
                                stm.addBatch();
                                if (i % 500 == 0) {
                                    stm.executeBatch();
                                    stm.clearBatch();
                                }
                            }
                            stm.executeBatch();
                            con.commit();
                            closeDB();
                        }

                    }
                } else if (latButton.equals("insert")) {
                    System.out.println(latTable + " GO!");
                    if (opText.getText().equals("reset")) {
                        Load load = new Load();
                        load.reflash();
                    } else {
                        String path = opText.getText();
                        System.out.println("Loading");
                    }

                }

            }

    }

}


