package com.example.cs307_proj2_final;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Menu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class HelloController {
    static Connection con;
    static PreparedStatement stmt = null;
    static boolean verbose = false;
    static ResultSet res;
    static ArrayList<Integer> latRes = new ArrayList<>();
    static String latTable = null;
    static String latId = null;
    static int opLength = 0;
    static int SIDofController;
    static String staff_type;

    @FXML
    private Text S1;

    @FXML
    private Text S2;

    @FXML
    private Text S3;

    @FXML
    private Text S4;

    @FXML
    private Text S5;


    @FXML
    private Menu MENU_help;

    @FXML
    private Menu MENU_manager;

    @FXML
    private Menu MENU_supply_staff;

    @FXML
    private Menu MENU_salesman;


    @FXML
    private PieChart PieChart;
    @FXML
    public Text TOTAL;

    @FXML
    private Text COMPLETE;

    @FXML
    private Text RATE;

    @FXML
    private Text RANK;

    @FXML
    private Text SID;

    @FXML
    private TableColumn<Item, String> C1;

    @FXML
    private TableColumn<Item, String> C2;

    @FXML
    private TableColumn<Item, String> C3;

    @FXML
    private TableColumn<Item, String> C4;

    @FXML
    private TableColumn<Item, String> C5;

    @FXML
    private TableColumn<Item, String> C6;

    @FXML
    private TableColumn<Item, String> C7;

    @FXML
    private TableColumn<Item, String> C8;

    @FXML
    private TableColumn<Item, String> C9;

    @FXML
    private TableColumn<Item, String> C10;

    @FXML
    private TableColumn<Item, String> C11;

    @FXML
    private TableView<Item> TABLEVIEW;

    public static ObservableList<Item> itemsListCell = FXCollections.observableArrayList();


    /**
     * Inti function
     * Loading information of user
     */


    @FXML
    private Text nowtime;

    static int total_num = 0, less200 = 0;
    static int complete = 0, total = 0;

    public void prepInti(int sid) throws SQLException, IOException {
        Properties login = new Properties();
        login.put("host", "localhost");
        login.put("user", "checker");
        login.put("password", "123456");
        login.put("database", "Proj_02");
        Properties prop = new Properties(login);


        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));
        inti(sid);
    }

    public void inti(int sid) throws SQLException, IOException {

        SIDofController = sid;
        //Load.main();
        //Function.main();


        PreparedStatement Total = con.prepareStatement("select count(*)from orders where salesman_num = ?");
        PreparedStatement Complete = con.prepareStatement("select count(*) from orders where salesman_num = ? and contract_type = 'Finished'");
        PreparedStatement Type = con.prepareStatement("select distinct * from staff where number = ?");
        PreparedStatement View = con.prepareStatement("select * from orders where salesman_num = ?");
        Total.setInt(1, sid);
        Complete.setInt(1, sid);
        Type.setInt(1, sid);
        View.setInt(1, sid);
        res = Total.executeQuery();
        res.next();

        res = Complete.executeQuery();
        res.next();

        res = Type.executeQuery();
        res.next();
        ArrayList<String> staff = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            staff.add(res.getString(i + 1));
        }
        res = View.executeQuery();

        System.out.println("The account type is " + staff.get(7));

        switch (staff.get(7)) {
            case "Salesman": {
                staff_type = "Salesman";
                view_order_select(res);
                double Asite = 100 * (double) complete / total;

                System.out.println(Asite);
                ObservableList<PieChart.Data> pieChartData =
                        FXCollections.observableArrayList(
                                new PieChart.Data("Finished", Asite),
                                new PieChart.Data("Progressing", 100 - Asite)
                        );
                PieChart.setData(pieChartData);
                PieChart.setMouseTransparent(true);
                PieChart.setLabelsVisible(false);
                S2.setText("Total");
                S3.setText("Complete");
                SID.setText(String.valueOf(sid));
                TOTAL.setText(String.valueOf(total));
                COMPLETE.setText(String.valueOf(complete));
                RATE.setText(String.format("%.2f", (double) Asite) + "%");
                RANK.setText(staff.get(7));

                MENU_manager.setVisible(false);
                MENU_supply_staff.setVisible(false);

                break;
            }
            case "Contracts Manager": {
                staff_type = "Contracts Manager";
                ResultSet manager;
                PreparedStatement getContract = con.prepareStatement("select * from orders where contract_num in (select contract_num from contract where contract_manager = ?)");
                getContract.setInt(1, sid);
                manager = getContract.executeQuery();

                view_order_select(manager);

                double Asite = 100 * (double) complete / total;

                System.out.println(Asite);
                ObservableList<PieChart.Data> pieChartData =
                        FXCollections.observableArrayList(
                                new PieChart.Data("Finished", Asite),
                                new PieChart.Data("Progressing", 100 - Asite)
                        );
                PieChart.setData(pieChartData);
                PieChart.setMouseTransparent(true);
                PieChart.setLabelsVisible(false);
                S2.setText("Total");
                S3.setText("Complete");
                SID.setText(String.valueOf(sid));
                TOTAL.setText(String.valueOf(total));
                COMPLETE.setText(String.valueOf(complete));
                RATE.setText(String.format("%.2f", (double) Asite) + "%");
                RANK.setText(staff.get(7));

                view_order_select(res);


                break;
            }
            case "Supply Staff": {
                staff_type = "Supply Staff";
                ResultSet supplystaff;
                PreparedStatement getProduct = con.prepareStatement("select * from product where supply_staff =  ?");
                getProduct.setInt(1, sid);
                supplystaff = getProduct.executeQuery();


                S2.setText("Total");
                S3.setText("Shortage");

                view_product_select(supplystaff);
                double Asite = 100 * (double) less200 / total_num;
                SID.setText(String.valueOf(sid));
                TOTAL.setText(String.valueOf(total_num));
                COMPLETE.setText(String.valueOf(less200));
                RATE.setText(String.format("%.2f", (double) Asite) + "%");
                RANK.setText(staff.get(7));

                ObservableList<PieChart.Data> pieChartData =
                        FXCollections.observableArrayList(
                                new PieChart.Data("Shortage", Asite),
                                new PieChart.Data("Normal Quantity", 100 - Asite)
                        );
                PieChart.setData(pieChartData);
                PieChart.setMouseTransparent(true);
                PieChart.setLabelsVisible(false);


                MENU_salesman.setVisible(false);
                MENU_manager.setVisible(false);

                break;
            }
        }


        DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        EventHandler<ActionEvent> eventHandler = e -> {
            nowtime.setText("The time is now: " + df.format(new java.util.Date()) + " (GMT+8)");
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();


    }

    void view_product_select(ResultSet res) throws SQLException {
        C1.setText("id");
        C2.setText("supply center");
        C3.setText("model");
        C4.setText("supply staff");
        C5.setText("date");
        C6.setText("unit price");
        C7.setText("quantity");

        C1.setCellValueFactory(data -> data.getValue().product_idProperty());
        C2.setCellValueFactory(data -> data.getValue().product_centerProperty());
        C3.setCellValueFactory(data -> data.getValue().product_modelProperty());
        C4.setCellValueFactory(data -> data.getValue().product_staffProperty());
        C5.setCellValueFactory(data -> data.getValue().product_dateProperty());
        C6.setCellValueFactory(data -> data.getValue().product_priceProperty());
        C7.setCellValueFactory(data -> data.getValue().product_quantityProperty());

        while (res.next()) {
            Item add = new Item();
            add.create_product(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7)
            );
            itemsListCell.add(add);
            if (res.getInt(7) < 200) less200++;
            total_num++;
        }
        TABLEVIEW.setItems(itemsListCell);
    }

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
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * API 1 Select
     * Only few parts
     */

    @FXML
    void Select_Model(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "SearchInfo.fxml"));
            Parent root = loader.load();
            SearchInfo ctrl = loader.getController();
            ctrl.fatherCtrl = this;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void clean() {
        TABLEVIEW.getItems().clear();
        C1.setText("C1");
        C2.setText("C2");
        C3.setText("C3");
        C4.setText("C4");
        C5.setText("C5");
        C6.setText("C6");
        C7.setText("C7");
        C8.setText("C8");
        C9.setText("C9");
        C10.setText("C10");
        C11.setText("C11");
    }

    void view_salesman_select(ResultSet res) throws SQLException {
        C1.setText("Id");
        C2.setText("Name");
        C3.setText("Age");
        C4.setText("Gender");
        C5.setText("Number");
        C6.setText("Center");
        C7.setText("Phone");
        C8.setText("Type");

        C1.setCellValueFactory(data -> data.getValue().salesman_idProperty());
        C2.setCellValueFactory(data -> data.getValue().salesman_nameProperty());
        C3.setCellValueFactory(data -> data.getValue().salesman_ageProperty());
        C4.setCellValueFactory(data -> data.getValue().salesman_genderProperty());
        C5.setCellValueFactory(data -> data.getValue().salesman_numberProperty());
        C6.setCellValueFactory(data -> data.getValue().salesman_supply_idProperty());
        C7.setCellValueFactory(data -> data.getValue().salesman_typeProperty());

        while (res.next()) {
            Item add = new Item();
            add.creat_salesman(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8));
            itemsListCell.add(add);
        }
        TABLEVIEW.setItems(itemsListCell);
    }

    void view_order_select(ResultSet res) throws SQLException {
        latTable = "orders";
        opLength = 11;
        latId = "order_id";
        C1.setText("id");
        C2.setText("contract_number");
        C3.setText("enterprise");
        C4.setText("product_model");
        C5.setText("quantity");
        C6.setText("manager_number");
        C7.setText("contract_date");
        C8.setText("estimated_delivery_date");
        C9.setText("lodgement_date");
        C10.setText("salesman_number");
        C11.setText("type");

        C1.setCellValueFactory(data -> data.getValue().order_idProperty());
        C2.setCellValueFactory(data -> data.getValue().order_contract_numberProperty());
        C3.setCellValueFactory(data -> data.getValue().order_enterpriseProperty());
        C4.setCellValueFactory(data -> data.getValue().order_product_modelProperty());
        C5.setCellValueFactory(data -> data.getValue().order_quantityProperty());
        C6.setCellValueFactory(data -> data.getValue().order_manager_numberProperty());
        C7.setCellValueFactory(data -> data.getValue().order_contract_dateProperty());
        C8.setCellValueFactory(data -> data.getValue().order_estimated_delivery_dateProperty());
        C9.setCellValueFactory(data -> data.getValue().order_lodgement_dateProperty());
        C10.setCellValueFactory(data -> data.getValue().order_salesman_numberProperty());
        C11.setCellValueFactory(data -> data.getValue().order_typeProperty());

        while (res.next()) {
            Item add = new Item();
            add.creat_order(
                    res.getString(11),
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getString(9),
                    res.getString(10)
            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(11)));

            if (res.getString(10).equals("Finished")) complete++;
            total++;
        }
        TABLEVIEW.setItems(itemsListCell);
    }

    void view_model_select(ResultSet res) throws SQLException {
        latTable = "model";
        opLength = 5;
        latId = "model_id";
        C1.setText("id");
        C2.setText("number");
        C3.setText("model");
        C4.setText("name");
        C5.setText("unit price");

        C1.setCellValueFactory((data -> data.getValue().model_idProperty()));
        C2.setCellValueFactory((data -> data.getValue().model_numberProperty()));
        C3.setCellValueFactory((data -> data.getValue().model_modelProperty()));
        C4.setCellValueFactory((data -> data.getValue().model_nameProperty()));
        C5.setCellValueFactory((data -> data.getValue().model_unit_priceProperty()));

        while (res.next()) {
            Item add = new Item();
            add.creat_model(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            );
            itemsListCell.add(add);
            latRes.add(Integer.valueOf(res.getString(1)));
        }
        TABLEVIEW.setItems(itemsListCell);
    }

    void prep_view_model_selest(String row, String way, String detail) throws SQLException {
        PreparedStatement select;
        switch (way) {
            case "acc":
                select = con.prepareStatement("select distinct * from model where " + row + " = " + "'" + detail + "'");
                break;
            case "ran":
                String[] list = detail.split(" ");
                select = con.prepareStatement("select distinct * from model where unit_price >= " + list[0] + " and id < " + list[1]);
                break;
            case "blu":
                select = con.prepareStatement("select distinct * from model where " + row + " like '%" + detail + "%'");
                break;
            default:
                return;
        }
        System.out.println(select);
        ResultSet next = select.executeQuery();
        clean();
        view_model_select(next);
    }

    void Delete_Model() throws SQLException {
        PreparedStatement del = con.prepareStatement("delete from  model  where id = ?");
        for (int i = 0; i < latRes.size(); i++) {
            del.setInt(1, latRes.get(i));
            del.execute();
            System.out.println(del);
        }
        con.commit();
    }

    void Insert_Model(int id, String number, String model, String name, int price) throws SQLException {
        PreparedStatement stm = con.prepareStatement("insert into model values (?,?,?,?,?)");
        stm.setInt(1, id);
        stm.setString(2, number);
        stm.setString(3, model);
        stm.setString(4, name);
        stm.setInt(5, price);
        stm.execute();
        con.commit();
    }

    void Update_Model(int id, String number, String model, String name, int price) throws SQLException {
        PreparedStatement stm = con.prepareStatement("update model set number = ?, model= ? , name = ? , unit_price = ? where id = ?");
        stm.setString(1, number);
        stm.setString(2, model);
        stm.setString(3, name);
        stm.setInt(4, price);
        stm.setInt(5, id);
        stm.execute();
        con.commit();
    }

    /**
     * API 2 Stock in
     * Using a prep function to load
     */

    @FXML
    void F_stock_in(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StockIn.fxml"));
            Parent root = loader.load();
            StockIn ctrl = loader.getController();
            ctrl.father = this;

            if (staff_type.equals("Contracts Manager"))
                ctrl.staff.setEditable(true);
            else ctrl.staff.setText(String.valueOf(SIDofController));

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stockInRun(String root) throws IOException, SQLException {
        BufferedReader infile =
                new BufferedReader(new FileReader(
                        root
                ));
        String line;
        infile.readLine();
        String[] parts;
        while ((line = infile.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 7) {
                stockIn(Integer.parseInt(parts[0]),
                        parts[1].substring(1) + "," + parts[2].substring(0, parts[2].length() - 1),
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]));
            } else {
                stockIn(Integer.parseInt(parts[0]),
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        parts[4],
                        Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[6]));
            }
        }
    }

    public static void stockIn(int id, String supply_center, String product_model, int supply_staff,
                               String date, int purchase_price, int quantity) throws SQLException {
        if (product_model.equals("ConnectingLine29") && supply_center.equals("Northern China")) {
            System.out.println();
        }

        PreparedStatement[] sql_list = new PreparedStatement[5];
        sql_list[0] = con.prepareStatement("select count(*) from model where model = '" + product_model + "'");
        sql_list[1] = con.prepareStatement("select count(*) from center where name = '" + supply_center + "'");
        sql_list[2] = con.prepareStatement("select count(*) from staff where number = " + supply_staff);
        sql_list[3] = con.prepareStatement("select count(*) from (select * from staff where number = " + supply_staff + ") as sub where type = 'Supply Staff'");
        sql_list[4] = con.prepareStatement("select supply_center from staff where number = " + supply_staff);

        for (int i = 0; i < 4; i++) {
            res = sql_list[i].executeQuery();
            if (!res.next()) {
                System.out.println(sql_list[i]);
                System.out.println("Information wrong");
                return;
            } else {
                String temp = res.getString(1);
                if (temp.equals("0")) {
                    System.out.print("invalid:");
                    if (i == 0) {
                        System.out.println("Product does not exist");
                    } else if (i == 1) {
                        System.out.println("Supply center does not exist");
                    } else if (i == 2) {
                        System.out.println("Staff does not exist");
                    } else {
                        System.out.println("The type of the supply staff is not supply_staff");
                    }
                    return;
                }
            }
        }
        res = sql_list[4].executeQuery();
        if (res.next()) {
            if (!res.getString(1).equals(supply_center)) {
                System.out.println("The supply center and the supply center to which the supply staff belongs do not match");
                return;
            }
        }

        PreparedStatement p = con.prepareStatement(
                "select count(*) from product where product_model='" + product_model +
                        "' and supply_center = '" + supply_center + "'");
        ResultSet resultSet = p.executeQuery();
        resultSet.next();
        if (resultSet.getString(1).equals("0")) {
            PreparedStatement stm = con.prepareStatement("insert into product values (?,?,?,?,?,?,?)");
            stm.setInt(1, id);
            stm.setString(2, supply_center);
            stm.setString(3, product_model);
            stm.setInt(4, supply_staff);
            Date date2 = Date.valueOf(date.replace("/", "-"));
            stm.setDate(5, date2);
            stm.setInt(6, purchase_price);
            stm.setInt(7, quantity);
            stm.execute();
            System.out.println("insert ok");
        } else {
            System.out.println("This data has existed, add quantity");
            PreparedStatement p1 = con.prepareStatement("select * from product where product_model='" + product_model + "' and supply_center = '" + supply_center + "'");
            ResultSet resultSet1 = p1.executeQuery();
            while (resultSet1.next()) {
                quantity = Integer.parseInt(resultSet1.getString(7)) + quantity;
            }
            PreparedStatement p2 = con.prepareStatement("select id from product where product_model='" + product_model + "' and supply_center = '" + supply_center + "'");
            ResultSet resultSet2 = p2.executeQuery();
            resultSet2.next();
            id = Integer.parseInt(resultSet2.getString(1));
            PreparedStatement stm = con.prepareStatement("update product set quantity = " + quantity + " where id = " + id);
            stm.execute();
            System.out.println("update ok");
        }
        con.commit();
        res.close();
    }

    /**
     * API 3 Place order
     * Using a prep function to load
     */

    @FXML
    void F_placeorder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PlaceOrder.fxml"));
            Parent root = loader.load();
            PlaceOrder ctrl = loader.getController();
            ctrl.father = this;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void placeOrderRun(String root) throws IOException, SQLException {
//        String root = "release-to-students/release-testcase1/task2_test_data_publish.csv";
        //String root = "testdata_final-5-20/task2_test_data_final_public.tsv";
        BufferedReader infile =
                new BufferedReader(new FileReader(
                        root
                ));
        String line;
        infile.readLine();
        String[] parts;
        while ((line = infile.readLine()) != null) {
            parts = line.split("\t");
//            parts = line.split(",");
            placeOrder(
                    parts[0],
                    parts[1],
                    parts[2],
                    Integer.parseInt(parts[3]),
                    Integer.parseInt(parts[4]),
                    parts[5],
                    parts[6],
                    parts[7],
                    Integer.parseInt(parts[8]),
                    parts[9]
            );

        }
    }

    public static void placeOrder(String contract_num, String enterprise, String product_model,
                                  int quantity, int contract_manager, String d, String d1, String d2,
                                  int salesman_num, String contract_type) throws SQLException {
        ResultSet res;
        if (product_model.equals("TwistedPairU0")) {
            System.out.println();
            System.out.println();
        }
        Date contract_date = Date.valueOf(d.replace("/", "-"));
        Date estimated_delivery_date = Date.valueOf(d1.replace("/", "-"));
        Date lodgement_date = Date.valueOf(d2.replace("/", "-"));

        PreparedStatement get_sc = con.prepareStatement("select supply_center from enterprise where name = '" + enterprise + "'");
        res = get_sc.executeQuery();
        res.next();
        String supply_center = res.getString(1);

        PreparedStatement get_sc2 = con.prepareStatement("select supply_center from staff where number = " + contract_manager);
        res = get_sc2.executeQuery();
        res.next();
        if (!supply_center.equals(res.getString(1))) {
            System.out.println("invalid: center not same");
            return;
        }
        get_sc2 = con.prepareStatement("select supply_center from staff where number = " + salesman_num);
        res = get_sc2.executeQuery();
        res.next();
        if (!supply_center.equals(res.getString(1))) {
            System.out.println("invalid: center not same");
            return;
        }

        PreparedStatement get_staff_type = con.prepareStatement("select type from staff where number = " + salesman_num);
        res = get_staff_type.executeQuery();
        res.next();
        String staff_type = res.getString(1);
        if (!Objects.equals(staff_type, "Salesman")) {
            System.out.println("salesman wrong " + staff_type);
            return;
        }

        PreparedStatement get_product_num = con.prepareStatement("select quantity from product where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
        res = get_product_num.executeQuery();
        res.next();
        int product_num;
        try {
            product_num = res.getInt(1);
        } catch (Exception e) {
            System.out.println("no such model");
            return;
        }

        if (product_num < quantity) {
            System.out.println("Quantity in an order larger than the stock");
            return;
        }
        PreparedStatement whether_contract_exist = con.prepareStatement("select count(*) from contract where contract_num = '" + contract_num + "'");
        res = whether_contract_exist.executeQuery();
        res.next();
        String temp = res.getString(1);
        if (Integer.parseInt(temp) == 0) {
            PreparedStatement insert0 = con.prepareStatement("insert into contract(contract_num, enterprise,contract_manager,contract_date,estimated_delivery_date,lodgement_date,contract_type) values(?,?,?,?,?,?,?)");
            insert0.setString(1, contract_num);
            insert0.setString(2, enterprise);
            insert0.setInt(3, contract_manager);
            insert0.setDate(4, contract_date);
            insert0.setDate(5, estimated_delivery_date);
            insert0.setDate(6, lodgement_date);
            insert0.setString(7, contract_type);
            insert0.execute();
            con.commit();
        }

        int updateQuantity = product_num - quantity;
        PreparedStatement update = con.prepareStatement("update product set quantity = " + updateQuantity + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
        update.execute();

        PreparedStatement insert = con.prepareStatement("insert into orders(contract_num, enterprise, product_model, " +
                "quantity, contract_manager, contract_date, estimated_delivery_date, " +
                "lodgement_date, salesman_num, contract_type)" + " values(?,?,?,?,?,?,?,?,?,?)");
        insert.setString(1, contract_num);
        insert.setString(2, enterprise);
        insert.setString(3, product_model);
        insert.setInt(4, quantity);
        insert.setInt(5, contract_manager);
        insert.setDate(6, contract_date);
        insert.setDate(7, estimated_delivery_date);
        insert.setDate(8, lodgement_date);
        insert.setInt(9, salesman_num);
        insert.setString(10, contract_type);
        insert.execute();

        con.commit();
        System.out.println("Insert successful");
    }

    /**
     * API 4 Update order
     * Using a prep function to load
     */

    @FXML
    void UPDATE_supplystaff(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UPDATE_supplystaff.fxml"));
            Parent root = loader.load();
            UpdateOrder ctrl = loader.getController();
            ctrl.father = this;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateOrderRun(String root) throws FileNotFoundException, SQLException {
        //String root = "testdata_final-5-20/update_final_test.tsv";
        Scanner scanner = new Scanner(new File(root));
//        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_update_test_data_publish.tsv"));
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            updateOrder(scanner.next(),
                    scanner.next(),
                    Integer.parseInt(scanner.next()),
                    Integer.parseInt(scanner.next()),
                    scanner.next(),
                    scanner.next());
        }
    }

    public static void updateOrder(String contract_num, String product_model, int salesman_num,
                                   int quantity, String a, String b) throws SQLException {
        ResultSet res;
        if (product_model.equals("TwistedPairU0")) {
            System.out.println();
            System.out.println();
        }

        Date estimated_delivery_date = Date.valueOf(a.replace("/", "-"));
        Date lodgement_date = Date.valueOf(b.replace("/", "-"));

        PreparedStatement get0 = con.prepareStatement("select count(*) from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'");
        res = get0.executeQuery();
        if (!res.next()) {
            System.out.println("wrong");
        } else {
            if (res.getInt(1) == 0) {
                System.out.println("salesman dont have this order");
                return;
            }
        }

        PreparedStatement get_sc2 = con.prepareStatement("select supply_center from staff where number = " + salesman_num);
        res = get_sc2.executeQuery();
        res.next();
        String supply_center = res.getString(1);

        PreparedStatement get = con.prepareStatement("select * from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = " + salesman_num);
        PreparedStatement products = con.prepareStatement("select * from product where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
        res = get.executeQuery();
        ResultSet res_product = products.executeQuery();
        res_product.next();
        int inventory = res_product.getInt(7);
        if (res.next()) {
            int origin_quantity = res.getInt(4);
            if ((quantity - origin_quantity) > inventory) {
                System.out.println("Wrong quantity update");
                return;
            } else if (quantity == 0) {
                System.out.println("delete this quantity 0 order");
                //update product quantity
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                con.prepareStatement(
                        "update product set quantity = " + (inventory + origin_quantity) + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'"
                ).execute();

                PreparedStatement s = con.prepareStatement("delete from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'");
                s.execute();
            } else {
                con.prepareStatement(
                        "update product set quantity = " + (inventory - quantity + origin_quantity) + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'"
                ).execute();
                PreparedStatement s = con.prepareStatement(
                        "update orders set (quantity,estimated_delivery_date,lodgement_date) = (" +
                                quantity + ",'" + estimated_delivery_date + "','" + lodgement_date + "')" +
                                " where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'"
                );
                s.execute();
            }
        }
        con.commit();
        System.out.println("Update successfully");
    }

    /**
     * API 5 Delete order
     * Using a prep function to load
     */

    @FXML
    void F_deleteorder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteOrder.fxml"));
            Parent root = loader.load();
            DeleteOrder ctrl = loader.getController();
            ctrl.father = this;
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrderRun(String root) throws FileNotFoundException, SQLException {
        //String root = "testdata_final-5-20/delete_final.tsv";
        Scanner scanner = new Scanner(new File(root));
//        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_delete_test_data_publish.tsv"));
        scanner.nextLine();
        while (scanner.hasNext()) {
            String t1 = scanner.next();
            int t2 = Integer.parseInt(scanner.next());
            int t3 = Integer.parseInt(scanner.next());
            deleteOrder(t1, t2, t3);
        }
    }

    public static void deleteOrder(String contract, int salesman, int sequence) throws SQLException {
        System.out.println("Order list");
        ArrayList<Integer> list = staffSelect(contract, salesman);
        if (sequence > 0 && sequence <= list.size()) {
            PreparedStatement test = con.prepareStatement("select * from orders where contract_num = '" + contract + "' and id = ?");
            test.setInt(1, list.get(sequence - 1));
            res = test.executeQuery();
            res.next();
            int delete_quantity = res.getInt(4);
            String product_model = res.getString(3);
            String enterprise = res.getString(2);
            PreparedStatement x = con.prepareStatement("select supply_center from enterprise where name = '" + enterprise + "'");
            ResultSet xx = x.executeQuery();
            xx.next();
            String supply_center = xx.getString(1);
            x = con.prepareStatement("select quantity from product where supply_center='" + supply_center + "' and product_model='" + product_model + "'");
            xx = x.executeQuery();
            xx.next();
            int inventory = xx.getInt(1);
            x = con.prepareStatement("update product set quantity = " + (delete_quantity + inventory) + " where supply_center = '" + supply_center + "' and product_model = '" + product_model + "'");
            x.execute();
            int temp = res.getInt(9);
            if (temp != salesman) {
                System.out.println("invalid because salesman wrong");
            }
            PreparedStatement delete = con.prepareStatement("delete from orders where contract_num = '" + contract + "' and id = ?");
            delete.setInt(1, list.get(sequence - 1));
            delete.execute();
            con.commit();
            System.out.println(delete);
            System.out.println("Delete successfully");
        } else {
            System.out.println("no delete");
        }

    }

    public static ArrayList<Integer> staffSelect(String contract, int num) throws SQLException {
        ResultSet res;
        PreparedStatement stm = con.prepareStatement("select * from orders where salesman_num = " + num + " and contract_num = '" + contract + "' order by estimated_delivery_date, product_model");
        res = stm.executeQuery();
        ArrayList<Integer> list = new ArrayList<>();
        while (res.next()) {
            list.add(res.getInt(11));
            for (int i = 1; i <= 10; i++) {
                System.out.printf("%-16s| ", res.getString(i));
            }
            System.out.println();
        }
        return list;
    }

    @FXML
    void F_load(ActionEvent event) throws SQLException, IOException {
        //Load.main();
        System.out.println("Successfully Load");
    }

    static File file = new File("Output.txt");

    @FXML
    void API6_10(ActionEvent event) throws SQLException, IOException {
        System.setOut(new PrintStream(new FileOutputStream(file)));
        ResultSet table2 = getAllStaffCount();
        int t11 = getContractCount();
        int t12 = getOrderCount();
        int t13 = getNeverSoldProductCount();
        ResultSet t14 = getFavoriteProductModel();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllInfo.fxml"));
        Parent root = loader.load();
        AllInfo ctrl = loader.getController();

        ctrl.text11.setText(" " + String.valueOf(t11));
        ctrl.text12.setText(" " + String.valueOf(t12));
        ctrl.text13.setText(" " + String.valueOf(t13));
        ctrl.text14.setText(" " + t14.getString(1) + " (" + t14.getString(2) + ")");

        System.out.println("Q6");
        System.out.printf("%-12s|%-6s|\n", "staff_type", "count");

        table2.next();
        ctrl.text21.setText(" " + table2.getString(2));
        System.out.printf("%-12s|%-6d|\n", table2.getString(1), table2.getInt(2));
        table2.next();
        ctrl.text22.setText(" " + table2.getString(2));
        System.out.printf("%-12s|%-6d|\n", table2.getString(1), table2.getInt(2));
        table2.next();
        ctrl.text23.setText(" " + table2.getString(2));
        System.out.printf("%-12s|%-6d|\n", table2.getString(1), table2.getInt(2));
        table2.next();
        ctrl.text24.setText(" " + table2.getString(2));
        System.out.printf("%-12s|%-6d|\n", t14.getString(1), t14.getInt(2));

        System.out.println("Q7");
        System.out.println("Number of contract: " + t11);
        System.out.println("Q8");
        System.out.println("Number of orders: " + t12);
        System.out.println("Q9");
        System.out.println("Number of orders: " + t13);
        System.out.println("Q10");
        System.out.printf("%-30s|%-6d|\n", t14.getString(1), t14.getInt(2));


        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static ResultSet getAllStaffCount() throws SQLException, IOException {
        ResultSet res;
        PreparedStatement staff_count = con.prepareStatement("select type staff_type, count(*) from staff where type = 'Director' or type = 'Contracts Manager' or type = 'Salesman' or type = 'Supply Staff' group by type");
        res = staff_count.executeQuery();
//        System.out.printf("%-12s|%-6s|\n", "staff_type", "count");
//        while (res.next())
//            System.out.printf("%-12s|%-6d|\n", res.getString(1), res.getInt(2));
        return res;
    }

    public static int getContractCount() throws SQLException {
        ResultSet res;
        PreparedStatement contract = con.prepareStatement("select count(*) from contract");
        res = contract.executeQuery();
        res.next();
        System.out.println("Number of contract: " + res.getInt(1));
        return res.getInt(1);
    }

    public static int getOrderCount() throws SQLException {
        ResultSet res;
        PreparedStatement order = con.prepareStatement("select count(*) from orders");
        res = order.executeQuery();
        res.next();
        System.out.println("Number of orders: " + res.getInt(1));
        return res.getInt(1);
    }

    public static int getNeverSoldProductCount() throws SQLException {
        ResultSet res;
        PreparedStatement order = con.prepareStatement("select count(*)\n" +
                "from (select distinct product_model from product) sub1\n" +
                "         left join\n" +
                "         (select distinct product_model from orders) sub2\n" +
                "         on sub1.product_model = sub2.product_model\n" +
                "where sub2.product_model is null");
        res = order.executeQuery();
        res.next();
        System.out.println("Number of orders: " + res.getInt(1));
        return res.getInt(1);
    }

    public static ResultSet getFavoriteProductModel() throws SQLException {
        ResultSet resultSet;
        PreparedStatement p = con.prepareStatement("select product_model, n\n" +
                "from (select max(num) n from (select sum(quantity) num from orders group by product_model) sub1) sub2\n" +
                "         join (select product_model, sum(quantity) num from orders group by product_model) sub3 on sub2.n = sub3.num");
        resultSet = p.executeQuery();
        resultSet.next();
        return resultSet;
    }

    @FXML
    void API11(ActionEvent event) throws SQLException, FileNotFoundException {

        System.setOut(new PrintStream(new FileOutputStream(file)));
        clean();
        C1.setText("Supply Center");
        C2.setText("Average");

        ResultSet set = getAvgStockByCenter();

        C1.setCellValueFactory(data -> data.getValue().countryProperty());
        C2.setCellValueFactory(data -> data.getValue().aveProperty());

        while (set.next()) {
            Item add = new Item();
            add.create_API11(set.getString(1), set.getString(2));
            itemsListCell.add(add);
        }
        TABLEVIEW.setItems(itemsListCell);

    }

    @FXML
    void API12(ActionEvent event) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("API12.fxml"));
        Parent root = loader.load();
        API12 ctrl = loader.getController();
        ctrl.father = this;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    void loadAPI12(String id) throws SQLException {
        clean();
        ResultSet set = getProductByNumber(id);
        C1.setText("Supply Center");
        C2.setText("Number");
        C3.setText("Model");
        C4.setText("Purchase Price");
        C5.setText("Quantity");

        C1.setCellValueFactory(data -> data.getValue().aProperty());
        C2.setCellValueFactory(data -> data.getValue().bProperty());
        C3.setCellValueFactory(data -> data.getValue().cProperty());
        C4.setCellValueFactory(data -> data.getValue().dProperty());
        C5.setCellValueFactory(data -> data.getValue().eProperty());

        while (set.next()) {
            Item add = new Item();
            add.create_API12(
                    set.getString(1),
                    set.getString(2),
                    set.getString(3),
                    set.getString(4),
                    set.getString(5)
            );
            itemsListCell.add(add);
        }
        TABLEVIEW.setItems(itemsListCell);

    }

    @FXML
    void API13(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("API13.fxml"));
        Parent root = loader.load();
        API13 ctrl = loader.getController();
        ctrl.father = this;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static ResultSet getAvgStockByCenter() throws SQLException {
        ResultSet resultSet = con.prepareStatement("select supply_center, cast((sum(quantity) * 1.0) / (count(product_model) * 1.0) as decimal(8, 1)) a from product group by supply_center order by supply_center;\n").executeQuery();
//        while (resultSet.next()) {
//            System.out.printf("%-20s|%-6.1f|\n", resultSet.getString(1), resultSet.getDouble(2));
//        }
        return resultSet;
    }

    public static ResultSet getProductByNumber(String product_number) throws SQLException {
        ResultSet resultSet = con.prepareStatement(
                "select supply_center, '" + product_number + "', model, purchase_price, quantity from product join (select distinct model.model from model where number = '" + product_number + "') sub1 on sub1.model = product_model"
        ).executeQuery();
        System.out.println("getProductByNumber");
//        while (resultSet.next()) {
//            System.out.printf("%-30s|%-6s|%-6s|%-6d|%-6d|\n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5));
//        }
        return resultSet;
    }

    public void getContractInfo(String contract_number, API13 api13) throws SQLException {
        System.out.println("ContractInfo:");
        System.out.printf("%-18s|%-22s|%-16s|%-16s\n", "contract_number", "contract_manager_name", "enterprise_name", "supply_center");
        PreparedStatement p1 = con.prepareStatement(
                "select contract_num, contract_manager, enterprise\n" +
                        "from contract\n" +
                        "where contract_num = '" + contract_number + "'"
        );
        ResultSet r = p1.executeQuery();
        r.next();
        String enterprise_name = r.getString(3);
        int contract_manager_num = r.getInt(2);
        PreparedStatement p2 = con.prepareStatement(
                "select name,supply_center\n" +
                        "from staff\n" +
                        "where number = " + contract_manager_num
        );
        r = p2.executeQuery();
        r.next();
        String contract_manager_name = r.getString(1);
        String supply_center = r.getString(2);

        api13.text1.setText(contract_number);
        api13.text2.setText(contract_manager_name);
        api13.text3.setText(enterprise_name);
        api13.text4.setText(supply_center);

        System.out.printf("%-18s|%-22s|%-16s|%-16s\n", contract_number, contract_manager_name, enterprise_name, supply_center);


        api13.father.clean();
        api13.father.C1.setText("Product Model");
        api13.father.C2.setText("Salesman name");
        api13.father.C3.setText("Quantity");
        api13.father.C4.setText("Unit Price");
        api13.father.C5.setText("Estimate Delivery Date");
        api13.father.C6.setText("Lodgement Date");

        api13.father.C1.setCellValueFactory(data -> data.getValue().AProperty());
        api13.father.C2.setCellValueFactory(data -> data.getValue().BProperty());
        api13.father.C3.setCellValueFactory(data -> data.getValue().CProperty());
        api13.father.C4.setCellValueFactory(data -> data.getValue().DProperty());
        api13.father.C5.setCellValueFactory(data -> data.getValue().EProperty());
        api13.father.C6.setCellValueFactory(data -> data.getValue().FProperty());

        System.out.println("All orders in contract including:");
        System.out.printf("%-18s|%-18s|%-12s|%-12s|%-22s|%-12s\n", "Product_model", "salesman_name", "quantity", "unit_price", "estimate_delivery_date", "lodgement_date");
        PreparedStatement p3 = con.prepareStatement("select product_model,salesman_num,quantity,estimated_delivery_date,lodgement_date\n" +
                "from orders where contract_num = '" + contract_number + "'");
        r = p3.executeQuery();
        while (r.next()) {
            String product_model = r.getString(1);
            int salesman_num = r.getInt(2);
            int quantity = r.getInt(3);
            Date estimated_delivery_date = r.getDate(4);
            Date lodgement_date = r.getDate(5);
            PreparedStatement p4 = con.prepareStatement(
                    "select name\n" +
                            "from staff\n" +
                            "where number = " + salesman_num
            );
            ResultSet rx = p4.executeQuery();
            rx.next();
            String saleman_name = rx.getString(1);
            PreparedStatement p5 = con.prepareStatement(
                    "select unit_price from model where model.model = '" + product_model + "'");
            rx = p5.executeQuery();
            rx.next();
            int unit_price = rx.getInt(1);
            System.out.printf("%-18s|%-18s|%-12d|%-12d|%-22s|%-12s\n", product_model, saleman_name, quantity, unit_price, estimated_delivery_date, lodgement_date);
            Item add = new Item();
            add.create_API13(product_model, saleman_name, String.valueOf(quantity), String.valueOf(unit_price), String.valueOf(estimated_delivery_date), String.valueOf(lodgement_date));
            api13.father.itemsListCell.add(add);
        }
        api13.father.TABLEVIEW.setItems(itemsListCell);
    }


    @FXML
    void OriginalInti(ActionEvent event) throws SQLException, IOException {
        clean();
        inti(SIDofController);
    }
}