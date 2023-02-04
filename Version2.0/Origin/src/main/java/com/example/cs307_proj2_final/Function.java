package com.example.cs307_proj2_final;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class Function {
    static Connection con;
    static PreparedStatement stmt = null;
    static boolean verbose = false;
    static ResultSet res;
    public static void main() throws SQLException, IOException {
        Properties login = new Properties();
        login.put("host", "localhost");
        login.put("user", "checker");
        login.put("password", "123456");
        login.put("database", "Proj_02");
        Properties prop = new Properties(login);

        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));

        stockInRun();
        System.out.println("____________________stock over____________________");

        placeOrderRun();
        System.out.println("____________________place over____________________");

        updateOrderRun();
        System.out.println("____________________update over____________________");

        deleteOrderRun();
        System.out.println("____________________delete over____________________");

        //6-8
        System.out.println(6);
        getAllStaffCount();
        System.out.println(7);
        getContractCount();
        System.out.println(8);
        getOrderCount();
        //9-10
        System.out.println(9);
        getNeverSoldProductCount();
        System.out.println(10);
        getFavoriteProductModel();
        //11-12
        System.out.println(11);
        getAvgStockByCenter();
        System.out.println(12);
        getProductByNumber("A50L172");

        //13
        System.out.println(13);
        getContractInfo("CSE0000106");
        getContractInfo("CSE0000209");
        getContractInfo("CSE0000306");
        closeDB();

        //normal_operator();

//        Scanner sc = new Scanner(System.in);
//        int i;
//        System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
//
//        while (sc.hasNext()) {
//            i = sc.nextInt();
//            switch (i) {
//                case 0:
//                    normal_operator();
//                    break;
//                case 1:
//                    stockIn();
//                    break;
//                case 2:
//                    placeOrder();
//                    break;
//                case 3:
//                    updateOrder();
//                    break;
//                case 4:
//                    deleteOrder();
//                    break;
//            }
//            if (i == 5) break;
//            System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
//        }


        closeDB();
    }
    public static void main(String[] args) throws SQLException, IOException {
        Properties login = new Properties();
        login.put("host", "localhost");
        login.put("user", "checker");
        login.put("password", "123456");
        login.put("database", "Proj_02");
        Properties prop = new Properties(login);

        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));

        stockInRun();
        System.out.println("____________________stock over____________________");

        placeOrderRun();
        System.out.println("____________________place over____________________");

        updateOrderRun();
        System.out.println("____________________update over____________________");

        deleteOrderRun();
        System.out.println("____________________delete over____________________");

        //6-8
        System.out.println(6);
        getAllStaffCount();
        System.out.println(7);
        getContractCount();
        System.out.println(8);
        getOrderCount();
        //9-10
        System.out.println(9);
        getNeverSoldProductCount();
        System.out.println(10);
        getFavoriteProductModel();
        //11-12
        System.out.println(11);
        getAvgStockByCenter();
        System.out.println(12);
        getProductByNumber("A50L172");

        //13
        System.out.println(13);
        getContractInfo("CSE0000106");
        getContractInfo("CSE0000209");
        getContractInfo("CSE0000306");
        closeDB();

        //normal_operator();

//        Scanner sc = new Scanner(System.in);
//        int i;
//        System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
//
//        while (sc.hasNext()) {
//            i = sc.nextInt();
//            switch (i) {
//                case 0:
//                    normal_operator();
//                    break;
//                case 1:
//                    stockIn();
//                    break;
//                case 2:
//                    placeOrder();
//                    break;
//                case 3:
//                    updateOrder();
//                    break;
//                case 4:
//                    deleteOrder();
//                    break;
//            }
//            if (i == 5) break;
//            System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
//        }


        closeDB();
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

    public static void getAllStaffCount() throws SQLException {
        ResultSet res;
        PreparedStatement staff_count = con.prepareStatement("select type staff_type, count(*) from staff where type = 'Director' or type = 'Contracts Manager' or type = 'Salesman' or type = 'Supply Staff' group by type");
        res = staff_count.executeQuery();
        System.out.printf("%-12s|%-6s|\n", "staff_type", "count");
        while (res.next())
            System.out.printf("%-12s|%-6d|\n", res.getString(1), res.getInt(2));
    }

    public static void getContractCount() throws SQLException {
        ResultSet res;
        PreparedStatement contract = con.prepareStatement("select count(*) from contract");
        res = contract.executeQuery();
        res.next();
        System.out.println("Number of contract: " + res.getInt(1));
    }

    public static void getOrderCount() throws SQLException {
        ResultSet res;
        PreparedStatement order = con.prepareStatement("select count(*) from orders");
        res = order.executeQuery();
        res.next();
        System.out.println("Number of orders: " + res.getInt(1));
    }

    public static void getNeverSoldProductCount() throws SQLException {
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
    }

    public static void getFavoriteProductModel() throws SQLException {
        ResultSet resultSet;
        PreparedStatement p = con.prepareStatement("select product_model, n\n" +
                "from (select max(num) n from (select sum(quantity) num from orders group by product_model) sub1) sub2\n" +
                "         join (select product_model, sum(quantity) num from orders group by product_model) sub3 on sub2.n = sub3.num");
        resultSet = p.executeQuery();
        System.out.println("the models with the highest sold quantity, and the number of sales");
        while (resultSet.next()) {
            System.out.printf("%-30s|%-6d|\n", resultSet.getString(1), resultSet.getInt(2));
        }
    }

    public static void getAvgStockByCenter() throws SQLException {
        ResultSet resultSet = con.prepareStatement("select supply_center, cast((sum(quantity) * 1.0) / (count(product_model) * 1.0) as decimal(8, 1)) a from product group by supply_center order by supply_center;\n").executeQuery();
        while (resultSet.next()) {
            System.out.printf("%-20s|%-6.1f|\n", resultSet.getString(1), resultSet.getDouble(2));
        }
    }

    public static void getProductByNumber(String product_number) throws SQLException {
        ResultSet resultSet = con.prepareStatement(
                "select supply_center, '" + product_number + "', model, purchase_price, quantity from product join (select distinct model.model from model where number = '" + product_number + "') sub1 on sub1.model = product_model"
        ).executeQuery();
        System.out.println("getProductByNumber");
        while (resultSet.next()) {
            System.out.printf("%-30s|%-6s|%-6s|%-6d|%-6d|\n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5));
        }

    }

    public static void getContractInfo(String contract_number) throws SQLException {
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
        System.out.printf("%-18s|%-22s|%-16s|%-16s\n", contract_number, contract_manager_name, enterprise_name, supply_center);

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

        }


    }

    public static void stockInRun() throws IOException, SQLException {
        String root = "testdata_final-5-20/in_stoke_test.csv";
//        String root = "release-to-students/release-testcase1/task1_in_stoke_test_data_publish.csv";
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

    public static void placeOrderRun() throws IOException, SQLException {
//        String root = "release-to-students/release-testcase1/task2_test_data_publish.csv";
        String root = "testdata_final-5-20/task2_test_data_final_public.tsv";
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

    public static void updateOrderRun() throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(new File("testdata_final-5-20/update_final_test.tsv"));
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

    public static void deleteOrderRun() throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(new File("testdata_final-5-20/delete_final.tsv"));
//        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_delete_test_data_publish.tsv"));
        scanner.nextLine();
        while (scanner.hasNext()) {
            String t1 = scanner.next();
            int t2 = Integer.parseInt(scanner.next());
            int t3 = Integer.parseInt(scanner.next());
            deleteOrder(t1, t2, t3);
        }
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
}


//package com.example.proj2_gui_new;
//
//import java.io.*;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.Objects;
//import java.util.Properties;
//import java.util.Scanner;
//
//public class Function {
//
//
//    static Connection con;
//    static PreparedStatement stmt = null;
//    static boolean verbose = false;
//    static ResultSet res;
//
//    public static void main() throws SQLException, IOException {
//        Properties login = new Properties();
//        login.put("host", "localhost");
//        login.put("user", "checker");
//        login.put("password", "123456");
//        login.put("database", "Proj_02");
//        Properties prop = new Properties(login);
//
//        openDB(prop.getProperty("host"), prop.getProperty("database"),
//                prop.getProperty("user"), prop.getProperty("password"));
//
//        PreparedStatement[] sql_list = new PreparedStatement[3];
//        sql_list[0] = con.prepareStatement("truncate table contract cascade");
//        sql_list[1] = con.prepareStatement("truncate table orders cascade");
//        sql_list[2] = con.prepareStatement("truncate table product cascade");
//
//        for (int i = 0; i < 3; i++) {
//            sql_list[i].executeBatch();
//            sql_list[i].clearBatch();
//        }
//        stockInRun();
//        System.out.println("____________________stock over____________________");
//
//        placeOrderRun();
//        System.out.println("____________________place over____________________");
//
//        updateOrderRun();
//        System.out.println("____________________update over____________________");
//
//        deleteOrderRun();
//        System.out.println("____________________delete over____________________");
//
//        //6-8
//        System.out.println(6);
//        getAllStaffCount();
//        System.out.println(7);
//        getContractCount();
//        System.out.println(8);
//        getOrderCount();
//        //9-10
//        System.out.println(9);
//        getNeverSoldProductCount();
//        System.out.println(10);
//        getFavoriteProductModel();
//        //11-12
//        System.out.println(11);
//        getAvgStockByCenter();
//        System.out.println(12);
//        getProductByNumber("A50L172");
//
//        //13
//        System.out.println(13);
//        getContractInfo("CSE0000106");
//        getContractInfo("CSE0000209");
//        getContractInfo("CSE0000306");
//        closeDB();
//
//        //normal_operator();
//
////        Scanner sc = new Scanner(System.in);
////        int i;
////        System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
////
////        while (sc.hasNext()) {
////            i = sc.nextInt();
////            switch (i) {
////                case 0:
////                    normal_operator();
////                    break;
////                case 1:
////                    stockIn();
////                    break;
////                case 2:
////                    placeOrder();
////                    break;
////                case 3:
////                    updateOrder();
////                    break;
////                case 4:
////                    deleteOrder();
////                    break;
////            }
////            if (i == 5) break;
////            System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
////        }
//
//        closeDB();
//    }
//
//    public static void main(String[] args) throws SQLException, IOException {
//        Properties login = new Properties();
//        login.put("host", "localhost");
//        login.put("user", "checker");
//        login.put("password", "123456");
//        login.put("database", "Proj_02");
//        Properties prop = new Properties(login);
//
//        openDB(prop.getProperty("host"), prop.getProperty("database"),
//                prop.getProperty("user"), prop.getProperty("password"));
//
//        PreparedStatement[] sql_list = new PreparedStatement[3];
//        sql_list[0] = con.prepareStatement("truncate table contract cascade");
//        sql_list[1] = con.prepareStatement("truncate table orders cascade");
//        sql_list[2] = con.prepareStatement("truncate table product cascade");
//
//        for (int i = 0; i < 3; i++) {
//            sql_list[i].executeBatch();
//            sql_list[i].clearBatch();
//        }
//        con.commit();
//        stockInRun();
//        System.out.println("____________________stock over____________________");
//
//        placeOrderRun();
//        System.out.println("____________________place over____________________");
//
//        updateOrderRun();
//        System.out.println("____________________update over____________________");
//
//        deleteOrderRun();
//        System.out.println("____________________delete over____________________");
//
//        //6-8
//        System.out.println(6);
//        getAllStaffCount();
//        System.out.println(7);
//        getContractCount();
//        System.out.println(8);
//        getOrderCount();
//        //9-10
//        System.out.println(9);
//        getNeverSoldProductCount();
//        System.out.println(10);
//        getFavoriteProductModel();
//        //11-12
//        System.out.println(11);
//        getAvgStockByCenter();
//        System.out.println(12);
//        getProductByNumber("A50L172");
//
//        //13
//        System.out.println(13);
//        getContractInfo("CSE0000106");
//        getContractInfo("CSE0000209");
//        getContractInfo("CSE0000306");
//        closeDB();
//
//        //normal_operator();
//
////        Scanner sc = new Scanner(System.in);
////        int i;
////        System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
////
////        while (sc.hasNext()) {
////            i = sc.nextInt();
////            switch (i) {
////                case 0:
////                    normal_operator();
////                    break;
////                case 1:
////                    stockIn();
////                    break;
////                case 2:
////                    placeOrder();
////                    break;
////                case 3:
////                    updateOrder();
////                    break;
////                case 4:
////                    deleteOrder();
////                    break;
////            }
////            if (i == 5) break;
////            System.out.println("0:normal_operator;  1:stockIn;  2:placeOrder;   3:updateOrder;  4:deleteOrder");
////        }
//
//        closeDB();
//    }
//
//
//
//    public static void normal_operator() throws SQLException {
//        Scanner in = new Scanner(System.in);
//        System.out.println("Input 0 to insert, 1 to select, 2 to delete, 3 to update, -1 to quit.");
//        int op = in.nextInt();
//        if (op == -1) return;
//        String table;
//        int res_length;
//        label:
//        while (true) {
//            System.out.println("Input which table to operator");
//            table = in.next();
//            switch (table) {
//                case "center":
//                    res_length = 2;
//                    break label;
//                case "enterprise":
//                    res_length = 6;
//                    break label;
//                case "model":
//                    res_length = 5;
//                    break label;
//                case "staff":
//                    res_length = 8;
//                    break label;
//            }
//            System.out.println("No such table");
//        }
//        System.out.println("Input 1 to operator one line, 2 to operator range lines");
//        int op_line = in.nextInt();
//        if (op_line == 1) {
//            PreparedStatement stm;
//            if (op == 0) {
//                System.out.println("Input insert information, totally " + res_length + " elements");
//                int id;
//                switch (table) {
//                    case "center":
//                        stm = con.prepareStatement("insert into " + table + " values (?,?)");
//                        id = in.nextInt();
//                        String name = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, name);
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "enterprise":
//                        stm = con.prepareStatement("insert into " + table + " values (?,?,?,?,?,?)");
//                        id = in.nextInt();
//                        stm.setInt(1, id);
//                        for (int i = 0; i < res_length - 1; i++)
//                            stm.setString(2 + i, in.next());
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "model":
//                        stm = con.prepareStatement("insert into " + table + " values (?,?,?,?)");
//                        id = in.nextInt();
//                        String number = in.next();
//                        String model = in.next();
//                        name = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, number);
//                        stm.setString(3, model);
//                        stm.setString(4, name);
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "staff":
//                        stm = con.prepareStatement("insert into " + table + " values (?,?,?,?,?,?,?,?)");
//                        id = in.nextInt();
//                        name = in.next();
//                        int age = in.nextInt();
//                        String gender = in.next();
//                        int num = in.nextInt();
//                        String supply_center = in.next();
//                        String mobile_number = in.next();
//                        String type = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, name);
//                        stm.setInt(3, age);
//                        stm.setString(4, gender);
//                        stm.setInt(5, num);
//                        stm.setString(6, supply_center);
//                        stm.setLong(7, Long.parseLong(mobile_number));
//                        stm.setString(8, type);
//                        stm.execute();
//                        con.commit();
//                        break;
//                }
//            }
//            if (op == 1) {
//                System.out.println("Input id");
//                int a = in.nextInt();
//                stm = con.prepareStatement("select * from " + table + " where id = " + a);
//                res = stm.executeQuery();
//                while (res.next()) {
//                    for (int i = 1; i <= res_length; i++) {
//                        System.out.printf("%-16s | ", res.getString(i));
//                    }
//                    System.out.println();
//                }
//            } else if (op == 2) {
//                System.out.println("Input id");
//                int a = in.nextInt();
//                stm = con.prepareStatement("delete from " + table + " where id =" + a);
//                stm.execute();
//                con.commit();
//            } else if (op == 3) {
//                System.out.println("Input update information, totally " + res_length + " elements");
//                int id;
//                switch (table) {
//                    case "center":
//                        stm = con.prepareStatement("update " + table + " set id = ?, name = ? where id = ?");
//                        id = in.nextInt();
//                        String name = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, name);
//                        stm.setInt(3, id);
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "enterprise":
//                        stm = con.prepareStatement("update " + table + " set id = ?, name = ?, country = ? , city = ?, supply_center = ? , industry = ? where id = ?");
//                        id = in.nextInt();
//                        stm.setInt(1, id);
//                        for (int i = 0; i < res_length - 1; i++)
//                            stm.setString(2 + i, in.next());
//                        stm.setInt(res_length + 1, id);
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "model":
//                        stm = con.prepareStatement("update " + table + " set id = ?, number = ?, model= ? , name = ? where id = ?");
//                        id = in.nextInt();
//                        String number = in.next();
//                        String model = in.next();
//                        name = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, number);
//                        stm.setString(3, model);
//                        stm.setString(4, name);
//                        stm.setInt(5, id);
//                        stm.execute();
//                        con.commit();
//                        break;
//                    case "staff":
//                        stm = con.prepareStatement("update " + table + " set id = ?, name = ?, age= ? , gender = ?, number= ?, supply_center= ?, mobile_number= ?, type= ?  where id = ?");
//                        id = in.nextInt();
//                        name = in.next();
//                        int age = in.nextInt();
//                        String gender = in.next();
//                        int num = in.nextInt();
//                        String supply_center = in.next();
//                        String mobile_number = in.next();
//                        String type = in.next();
//                        stm.setInt(1, id);
//                        stm.setString(2, name);
//                        stm.setInt(3, age);
//                        stm.setString(4, gender);
//                        stm.setInt(5, num);
//                        stm.setString(6, supply_center);
//                        stm.setLong(7, Long.parseLong(mobile_number));
//                        stm.setString(8, type);
//                        stm.setInt(9, id);
//                        stm.execute();
//                        con.commit();
//                        break;
//                }
//            }
//        } else if (op_line == 2) {
//            System.out.println("Input range");
//            int a = in.nextInt(), b = in.nextInt();
//            PreparedStatement stm;
//            if (op == 0) {
//                for (int x = a; x <= b; x++) {
//                    System.out.print("number" + (x - a + 1) + ":");
//                    int id;
//                    switch (table) {
//                        case "center":
//                            stm = con.prepareStatement("insert into " + table + " values (?,?)");
//                            id = in.nextInt();
//                            String name = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, name);
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "enterprise":
//                            stm = con.prepareStatement("insert into " + table + " values (?,?,?,?,?,?)");
//                            id = in.nextInt();
//                            stm.setInt(1, id);
//                            for (int i = 0; i < res_length - 1; i++)
//                                stm.setString(2 + i, in.next());
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "model":
//                            stm = con.prepareStatement("insert into " + table + " values (?,?,?,?)");
//                            id = in.nextInt();
//                            String number = in.next();
//                            String model = in.next();
//                            name = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, number);
//                            stm.setString(3, model);
//                            stm.setString(4, name);
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "staff":
//                            stm = con.prepareStatement("insert into " + table + " values (?,?,?,?,?,?,?,?)");
//
//                            id = in.nextInt();
//                            name = in.next();
//                            int age = in.nextInt();
//                            String gender = in.next();
//                            int num = in.nextInt();
//                            String supply_center = in.next();
//                            String mobile_number = in.next();
//                            String type = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, name);
//                            stm.setInt(3, age);
//                            stm.setString(4, gender);
//                            stm.setInt(5, num);
//                            stm.setString(6, supply_center);
//                            stm.setLong(7, Long.parseLong(mobile_number));
//                            stm.setString(8, type);
//                            stm.execute();
//                            con.commit();
//                            break;
//                    }
//                }
//            } else if (op == 1) {
//                stm = con.prepareStatement("select * from " + table + " where id >= " + a + " and id <= " + b);
//                res = stm.executeQuery();
//                while (res.next()) {
//                    for (int i = 1; i <= res_length; i++) {
//                        System.out.printf("%-16s | ", res.getString(i));
//                    }
//                    System.out.println();
//                }
//            } else if (op == 2) {
//                stm = con.prepareStatement("delete from " + table + " where id >= " + a + " and id <= " + b);
//                stm.execute();
//                con.commit();
//            } else if (op == 3) {
//                for (int x = a; x <= b; x++) {
//                    System.out.print("number" + (x - a + 1) + ":");
//                    System.out.println("Input update information, totally " + res_length + " elements");
//                    int id;
//                    switch (table) {
//                        case "center":
//                            stm = con.prepareStatement("update " + table + " set id = ?, name = ? where id = ?");
//                            id = in.nextInt();
//                            String name = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, name);
//                            stm.setInt(res_length + 1, id);
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "enterprise":
//                            stm = con.prepareStatement("update " + table + " set id = ?, name = ?, country = ? , city = ?, supply_center = ? , industry = ? where id = ?");
//                            id = in.nextInt();
//                            stm.setInt(1, id);
//                            for (int i = 0; i < res_length - 1; i++)
//                                stm.setString(2 + i, in.next());
//                            stm.setInt(res_length + 1, id);
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "model":
//                            stm = con.prepareStatement("update " + table + " set id = ?, number = ?, model= ? , name = ? where id = ?");
//                            id = in.nextInt();
//                            String number = in.next();
//                            String model = in.next();
//                            name = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, number);
//                            stm.setString(3, model);
//                            stm.setString(4, name);
//                            stm.setInt(5, id);
//                            stm.execute();
//                            con.commit();
//                            break;
//                        case "staff":
//                            stm = con.prepareStatement("update " + table + " set id = ?, name = ?, age= ? , gender = ?, number= ?, supply_center= ?, mobile_number= ?, type= ?  where id = ?");
//                            id = in.nextInt();
//                            name = in.next();
//                            int age = in.nextInt();
//                            String gender = in.next();
//                            int num = in.nextInt();
//                            String supply_center = in.next();
//                            String mobile_number = in.next();
//                            String type = in.next();
//                            stm.setInt(1, id);
//                            stm.setString(2, name);
//                            stm.setInt(3, age);
//                            stm.setString(4, gender);
//                            stm.setInt(5, num);
//                            stm.setString(6, supply_center);
//                            stm.setLong(7, Long.parseLong(mobile_number));
//                            stm.setString(8, type);
//                            stm.setInt(9, id);
//                            stm.execute();
//                            con.commit();
//                            break;
//                    }
//                }
//            }
//        }
//    }
//
//    public static void stockIn(int id, String supply_center, String product_model, int supply_staff,
//                               String date, int purchase_price, int quantity) throws SQLException {
//        if (product_model.equals("ConnectingLine29") && supply_center.equals("Northern China")) {
//            System.out.println();
//        }
//
//        PreparedStatement[] sql_list = new PreparedStatement[5];
//        sql_list[0] = con.prepareStatement("select count(*) from model where model = '" + product_model + "'");
//        sql_list[1] = con.prepareStatement("select count(*) from center where name = '" + supply_center + "'");
//        sql_list[2] = con.prepareStatement("select count(*) from staff where number = " + supply_staff);
//        sql_list[3] = con.prepareStatement("select count(*) from (select * from staff where number = " + supply_staff + ") as sub where type = 'Supply Staff'");
//        sql_list[4] = con.prepareStatement("select supply_center from staff where number = " + supply_staff);
//
//        for (int i = 0; i < 4; i++) {
//            res = sql_list[i].executeQuery();
//            if (!res.next()) {
//                System.out.println(sql_list[i]);
//                System.out.println("Information wrong");
//                return;
//            } else {
//                String temp = res.getString(1);
//                if (temp.equals("0")) {
//                    System.out.print("invalid:");
//                    if (i == 0) {
//                        System.out.println("Product does not exist");
//                    } else if (i == 1) {
//                        System.out.println("Supply center does not exist");
//                    } else if (i == 2) {
//                        System.out.println("Staff does not exist");
//                    } else {
//                        System.out.println("The type of the supply staff is not supply_staff");
//                    }
//                    return;
//                }
//            }
//        }
//        res = sql_list[4].executeQuery();
//        if (res.next()) {
//            if (!res.getString(1).equals(supply_center)) {
//                System.out.println("The supply center and the supply center to which the supply staff belongs do not match");
//                return;
//            }
//        }
//
//        PreparedStatement p = con.prepareStatement(
//                "select count(*) from product where product_model='" + product_model +
//                        "' and supply_center = '" + supply_center + "'");
//        ResultSet resultSet = p.executeQuery();
//        resultSet.next();
//        if (resultSet.getString(1).equals("0")) {
//            PreparedStatement stm = con.prepareStatement("insert into product values (?,?,?,?,?,?,?)");
//            stm.setInt(1, id);
//            stm.setString(2, supply_center);
//            stm.setString(3, product_model);
//            stm.setInt(4, supply_staff);
//            Date date2 = Date.valueOf(date.replace("/", "-"));
//            stm.setDate(5, date2);
//            stm.setInt(6, purchase_price);
//            stm.setInt(7, quantity);
//            stm.execute();
//            System.out.println("insert ok");
//        } else {
//            System.out.println("This data has existed, add quantity");
//            PreparedStatement p1 = con.prepareStatement("select * from product where product_model='" + product_model + "' and supply_center = '" + supply_center + "'");
//            ResultSet resultSet1 = p1.executeQuery();
//            while (resultSet1.next()) {
//                quantity = Integer.parseInt(resultSet1.getString(7)) + quantity;
//            }
//            PreparedStatement p2 = con.prepareStatement("select id from product where product_model='" + product_model + "' and supply_center = '" + supply_center + "'");
//            ResultSet resultSet2 = p2.executeQuery();
//            resultSet2.next();
//            id = Integer.parseInt(resultSet2.getString(1));
//            PreparedStatement stm = con.prepareStatement("update product set quantity = " + quantity + " where id = " + id);
//            stm.execute();
//            System.out.println("update ok");
//        }
//        con.commit();
//        res.close();
//    }
//
//    public static void placeOrder(String contract_num, String enterprise, String product_model,
//                                  int quantity, int contract_manager, String d, String d1, String d2,
//                                  int salesman_num, String contract_type) throws SQLException {
//        ResultSet res;
//        if (product_model.equals("TwistedPairU0")) {
//            System.out.println();
//            System.out.println();
//        }
//        Date contract_date = Date.valueOf(d.replace("/", "-"));
//        Date estimated_delivery_date = Date.valueOf(d1.replace("/", "-"));
//        Date lodgement_date = Date.valueOf(d2.replace("/", "-"));
//
//        PreparedStatement get_sc = con.prepareStatement("select supply_center from enterprise where name = '" + enterprise + "'");
//        res = get_sc.executeQuery();
//        res.next();
//        String supply_center = res.getString(1);
//
//        PreparedStatement get_sc2 = con.prepareStatement("select supply_center from staff where number = " + contract_manager);
//        res = get_sc2.executeQuery();
//        res.next();
//        if (!supply_center.equals(res.getString(1))) {
//            System.out.println("invalid: center not same");
//            return;
//        }
//        get_sc2 = con.prepareStatement("select supply_center from staff where number = " + salesman_num);
//        res = get_sc2.executeQuery();
//        res.next();
//        if (!supply_center.equals(res.getString(1))) {
//            System.out.println("invalid: center not same");
//            return;
//        }
//
//        PreparedStatement get_staff_type = con.prepareStatement("select type from staff where number = " + salesman_num);
//        res = get_staff_type.executeQuery();
//        res.next();
//        String staff_type = res.getString(1);
//        if (!Objects.equals(staff_type, "Salesman")) {
//            System.out.println("salesman wrong " + staff_type);
//            return;
//        }
//
//        PreparedStatement get_product_num = con.prepareStatement("select quantity from product where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
//        res = get_product_num.executeQuery();
//        res.next();
//        int product_num;
//        try {
//            product_num = res.getInt(1);
//        } catch (Exception e) {
//            System.out.println("no such model");
//            return;
//        }
//
//        if (product_num < quantity) {
//            System.out.println("Quantity in an order larger than the stock");
//            return;
//        }
//        PreparedStatement whether_contract_exist = con.prepareStatement("select count(*) from contract where contract_num = '" + contract_num + "'");
//        res = whether_contract_exist.executeQuery();
//        res.next();
//        String temp = res.getString(1);
//        if (Integer.parseInt(temp) == 0) {
//            PreparedStatement insert0 = con.prepareStatement("insert into contract(contract_num, enterprise,contract_manager,contract_date,estimated_delivery_date,lodgement_date,contract_type) values(?,?,?,?,?,?,?)");
//            insert0.setString(1, contract_num);
//            insert0.setString(2, enterprise);
//            insert0.setInt(3, contract_manager);
//            insert0.setDate(4, contract_date);
//            insert0.setDate(5, estimated_delivery_date);
//            insert0.setDate(6, lodgement_date);
//            insert0.setString(7, contract_type);
//            insert0.execute();
//            con.commit();
//        }
//
//        int updateQuantity = product_num - quantity;
//        PreparedStatement update = con.prepareStatement("update product set quantity = " + updateQuantity + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
//        update.execute();
//
//        PreparedStatement insert = con.prepareStatement("insert into orders(contract_num, enterprise, product_model, " +
//                "quantity, contract_manager, contract_date, estimated_delivery_date, " +
//                "lodgement_date, salesman_num, contract_type)" + " values(?,?,?,?,?,?,?,?,?,?)");
//        insert.setString(1, contract_num);
//        insert.setString(2, enterprise);
//        insert.setString(3, product_model);
//        insert.setInt(4, quantity);
//        insert.setInt(5, contract_manager);
//        insert.setDate(6, contract_date);
//        insert.setDate(7, estimated_delivery_date);
//        insert.setDate(8, lodgement_date);
//        insert.setInt(9, salesman_num);
//        insert.setString(10, contract_type);
//        insert.execute();
//
//        con.commit();
//        System.out.println("Insert successful");
//    }
//
//
//    public static void updateOrder(String contract_num, String product_model, int salesman_num,
//                                   int quantity, String a, String b) throws SQLException {
//        ResultSet res;
//        if (product_model.equals("TwistedPairU0")) {
//            System.out.println();
//            System.out.println();
//        }
//
//        Date estimated_delivery_date = Date.valueOf(a.replace("/", "-"));
//        Date lodgement_date = Date.valueOf(b.replace("/", "-"));
//
//        PreparedStatement get0 = con.prepareStatement("select count(*) from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'");
//        res = get0.executeQuery();
//        if (!res.next()) {
//            System.out.println("wrong");
//        } else {
//            if (res.getInt(1) == 0) {
//                System.out.println("salesman dont have this order");
//                return;
//            }
//        }
//
//        PreparedStatement get_sc2 = con.prepareStatement("select supply_center from staff where number = " + salesman_num);
//        res = get_sc2.executeQuery();
//        res.next();
//        String supply_center = res.getString(1);
//
//        PreparedStatement get = con.prepareStatement("select * from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = " + salesman_num);
//        PreparedStatement products = con.prepareStatement("select * from product where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'");
//        res = get.executeQuery();
//        ResultSet res_product = products.executeQuery();
//        res_product.next();
//        int inventory = res_product.getInt(7);
//        if (res.next()) {
//            int origin_quantity = res.getInt(4);
//            if ((quantity - origin_quantity) > inventory) {
//                System.out.println("Wrong quantity update");
//                return;
//            } else if (quantity == 0) {
//                System.out.println("delete this quantity 0 order");
//                //update product quantity
//                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
//                con.prepareStatement(
//                        "update product set quantity = " + (inventory + origin_quantity) + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'"
//                ).execute();
//
//                PreparedStatement s = con.prepareStatement("delete from orders where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'");
//                s.execute();
//            } else {
//                con.prepareStatement(
//                        "update product set quantity = " + (inventory - quantity + origin_quantity) + " where product_model = '" + product_model + "' and supply_center = '" + supply_center + "'"
//                ).execute();
//                PreparedStatement s = con.prepareStatement(
//                        "update orders set (quantity,estimated_delivery_date,lodgement_date) = (" +
//                                quantity + ",'" + estimated_delivery_date + "','" + lodgement_date + "')" +
//                                " where contract_num = '" + contract_num + "' and product_model = '" + product_model + "' and salesman_num = '" + salesman_num + "'"
//                );
//                s.execute();
//            }
//        }
//        con.commit();
//        System.out.println("Update successfully");
//    }
//
//    public static void deleteOrder(String contract, int salesman, int sequence) throws SQLException {
//        System.out.println("Order list");
//        ArrayList<Integer> list = staffSelect(contract, salesman);
//        if (sequence > 0 && sequence <= list.size()) {
//            PreparedStatement test = con.prepareStatement("select * from orders where contract_num = '" + contract + "' and id = ?");
//            test.setInt(1, list.get(sequence - 1));
//            res = test.executeQuery();
//            res.next();
//            int delete_quantity = res.getInt(4);
//            String product_model = res.getString(3);
//            String enterprise = res.getString(2);
//            PreparedStatement x = con.prepareStatement("select supply_center from enterprise where name = '" + enterprise + "'");
//            ResultSet xx = x.executeQuery();
//            xx.next();
//            String supply_center = xx.getString(1);
//            x = con.prepareStatement("select quantity from product where supply_center='" + supply_center + "' and product_model='" + product_model + "'");
//            xx = x.executeQuery();
//            xx.next();
//            int inventory = xx.getInt(1);
//            x = con.prepareStatement("update product set quantity = " + (delete_quantity + inventory) + " where supply_center = '" + supply_center + "' and product_model = '" + product_model + "'");
//            x.execute();
//            int temp = res.getInt(9);
//            if (temp != salesman) {
//                System.out.println("invalid because salesman wrong");
//            }
//            PreparedStatement delete = con.prepareStatement("delete from orders where contract_num = '" + contract + "' and id = ?");
//            delete.setInt(1, list.get(sequence - 1));
//            delete.execute();
//            con.commit();
//            System.out.println(delete);
//            System.out.println("Delete successfully");
//        } else {
//            System.out.println("no delete");
//        }
//
//    }
//
//    public static ArrayList<Integer> staffSelect(String contract, int num) throws SQLException {
//        ResultSet res;
//        PreparedStatement stm = con.prepareStatement("select * from orders where salesman_num = " + num + " and contract_num = '" + contract + "' order by estimated_delivery_date, product_model");
//        res = stm.executeQuery();
//        ArrayList<Integer> list = new ArrayList<>();
//        while (res.next()) {
//            list.add(res.getInt(11));
//            for (int i = 1; i <= 10; i++) {
//                System.out.printf("%-16s| ", res.getString(i));
//            }
//            System.out.println();
//        }
//        return list;
//    }
//
//    public static void getAllStaffCount() throws SQLException {
//        ResultSet res;
//        PreparedStatement staff_count = con.prepareStatement("select type staff_type, count(*) from staff where type = 'Director' or type = 'Contracts Manager' or type = 'Salesman' or type = 'Supply Staff' group by type");
//        res = staff_count.executeQuery();
//        System.out.printf("%-12s|%-6s|\n", "staff_type", "count");
//        while (res.next())
//            System.out.printf("%-12s|%-6d|\n", res.getString(1), res.getInt(2));
//    }
//
//    public static void getContractCount() throws SQLException {
//        ResultSet res;
//        PreparedStatement contract = con.prepareStatement("select count(*) from contract");
//        res = contract.executeQuery();
//        res.next();
//        System.out.println("Number of contract: " + res.getInt(1));
//    }
//
//    public static void getOrderCount() throws SQLException {
//        ResultSet res;
//        PreparedStatement order = con.prepareStatement("select count(*) from orders");
//        res = order.executeQuery();
//        res.next();
//        System.out.println("Number of orders: " + res.getInt(1));
//    }
//
//    public static void getNeverSoldProductCount() throws SQLException {
//        ResultSet res;
//        PreparedStatement order = con.prepareStatement("select count(*)\n" +
//                "from (select distinct product_model from product) sub1\n" +
//                "         left join\n" +
//                "         (select distinct product_model from orders) sub2\n" +
//                "         on sub1.product_model = sub2.product_model\n" +
//                "where sub2.product_model is null");
//        res = order.executeQuery();
//        res.next();
//        System.out.println("Number of orders: " + res.getInt(1));
//    }
//
//    public static void getFavoriteProductModel() throws SQLException {
//        ResultSet resultSet;
//        PreparedStatement p = con.prepareStatement("select product_model, n\n" +
//                "from (select max(num) n from (select sum(quantity) num from orders group by product_model) sub1) sub2\n" +
//                "         join (select product_model, sum(quantity) num from orders group by product_model) sub3 on sub2.n = sub3.num");
//        resultSet = p.executeQuery();
//        System.out.println("the models with the highest sold quantity, and the number of sales");
//        while (resultSet.next()) {
//            System.out.printf("%-30s|%-6d|\n", resultSet.getString(1), resultSet.getInt(2));
//        }
//    }
//
//    public static void getAvgStockByCenter() throws SQLException {
//        ResultSet resultSet = con.prepareStatement("select supply_center, cast((sum(quantity) * 1.0) / (count(product_model) * 1.0) as decimal(8, 1)) a from product group by supply_center order by supply_center;\n").executeQuery();
//        while (resultSet.next()) {
//            System.out.printf("%-20s|%-6.1f|\n", resultSet.getString(1), resultSet.getDouble(2));
//        }
//    }
//
//    public static void getProductByNumber(String product_number) throws SQLException {
//        ResultSet resultSet = con.prepareStatement(
//                "select supply_center, '" + product_number + "', model, purchase_price, quantity from product join (select distinct model.model from model where number = '" + product_number + "') sub1 on sub1.model = product_model"
//        ).executeQuery();
//        System.out.println("getProductByNumber");
//        while (resultSet.next()) {
//            System.out.printf("%-30s|%-6s|%-6s|%-6d|%-6d|\n", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getInt(5));
//        }
//
//    }
//
//    public static void getContractInfo(String contract_number) throws SQLException {
//        System.out.println("ContractInfo:");
//        System.out.printf("%-18s|%-22s|%-16s|%-16s\n", "contract_number", "contract_manager_name", "enterprise_name", "supply_center");
//        PreparedStatement p1 = con.prepareStatement(
//                "select contract_num, contract_manager, enterprise\n" +
//                        "from contract\n" +
//                        "where contract_num = '" + contract_number + "'"
//        );
//        ResultSet r = p1.executeQuery();
//        r.next();
//        String enterprise_name = r.getString(3);
//        int contract_manager_num = r.getInt(2);
//        PreparedStatement p2 = con.prepareStatement(
//                "select name,supply_center\n" +
//                        "from staff\n" +
//                        "where number = " + contract_manager_num
//        );
//        r = p2.executeQuery();
//        r.next();
//        String contract_manager_name = r.getString(1);
//        String supply_center = r.getString(2);
//        System.out.printf("%-18s|%-22s|%-16s|%-16s\n", contract_number, contract_manager_name, enterprise_name, supply_center);
//
//        System.out.println("All orders in contract including:");
//        System.out.printf("%-18s|%-18s|%-12s|%-12s|%-22s|%-12s\n", "Product_model", "salesman_name", "quantity", "unit_price", "estimate_delivery_date", "lodgement_date");
//        PreparedStatement p3 = con.prepareStatement("select product_model,salesman_num,quantity,estimated_delivery_date,lodgement_date\n" +
//                "from orders where contract_num = '" + contract_number + "'");
//        r = p3.executeQuery();
//        while (r.next()) {
//            String product_model = r.getString(1);
//            int salesman_num = r.getInt(2);
//            int quantity = r.getInt(3);
//            Date estimated_delivery_date = r.getDate(4);
//            Date lodgement_date = r.getDate(5);
//            PreparedStatement p4 = con.prepareStatement(
//                    "select name\n" +
//                            "from staff\n" +
//                            "where number = " + salesman_num
//            );
//            ResultSet rx = p4.executeQuery();
//            rx.next();
//            String saleman_name = rx.getString(1);
//            PreparedStatement p5 = con.prepareStatement(
//                    "select unit_price from model where model.model = '" + product_model + "'");
//            rx = p5.executeQuery();
//            rx.next();
//            int unit_price = rx.getInt(1);
//            System.out.printf("%-18s|%-18s|%-12d|%-12d|%-22s|%-12s\n", product_model, saleman_name, quantity, unit_price, estimated_delivery_date, lodgement_date);
//
//        }
//
//
//    }
//
//    public static void stockInRun() throws IOException, SQLException {
//        String root = "testdata_final-5-20/in_stoke_test.csv";
////        String root = "release-to-students/release-testcase1/task1_in_stoke_test_data_publish.csv";
//        BufferedReader infile =
//                new BufferedReader(new FileReader(
//                        root
//                ));
//        String line;
//        infile.readLine();
//        String[] parts;
//        while ((line = infile.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 7) {
//                stockIn(Integer.parseInt(parts[0]),
//                        parts[1].substring(1) + "," + parts[2].substring(0, parts[2].length() - 1),
//                        parts[3],
//                        Integer.parseInt(parts[4]),
//                        parts[5],
//                        Integer.parseInt(parts[6]),
//                        Integer.parseInt(parts[7]));
//            } else {
//                stockIn(Integer.parseInt(parts[0]),
//                        parts[1],
//                        parts[2],
//                        Integer.parseInt(parts[3]),
//                        parts[4],
//                        Integer.parseInt(parts[5]),
//                        Integer.parseInt(parts[6]));
//            }
//        }
//    }
//
//    public static void placeOrderRun() throws IOException, SQLException {
////        String root = "release-to-students/release-testcase1/task2_test_data_publish.csv";
//        String root = "testdata_final-5-20/task2_test_data_final_public.tsv";
//        BufferedReader infile =
//                new BufferedReader(new FileReader(
//                        root
//                ));
//        String line;
//        infile.readLine();
//        String[] parts;
//        while ((line = infile.readLine()) != null) {
//            parts = line.split("\t");
////            parts = line.split(",");
//            placeOrder(
//                    parts[0],
//                    parts[1],
//                    parts[2],
//                    Integer.parseInt(parts[3]),
//                    Integer.parseInt(parts[4]),
//                    parts[5],
//                    parts[6],
//                    parts[7],
//                    Integer.parseInt(parts[8]),
//                    parts[9]
//            );
//
//        }
//    }
//
//    public static void updateOrderRun() throws FileNotFoundException, SQLException {
//        Scanner scanner = new Scanner(new File("testdata_final-5-20/update_final_test.tsv"));
////        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_update_test_data_publish.tsv"));
//        scanner.nextLine();
//        while (scanner.hasNextLine()) {
//            updateOrder(scanner.next(),
//                    scanner.next(),
//                    Integer.parseInt(scanner.next()),
//                    Integer.parseInt(scanner.next()),
//                    scanner.next(),
//                    scanner.next());
//        }
//    }
//
//    public static void deleteOrderRun() throws FileNotFoundException, SQLException {
//        Scanner scanner = new Scanner(new File("testdata_final-5-20/delete_final.tsv"));
////        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_delete_test_data_publish.tsv"));
//        scanner.nextLine();
//        while (scanner.hasNext()) {
//            String t1 = scanner.next();
//            int t2 = Integer.parseInt(scanner.next());
//            int t3 = Integer.parseInt(scanner.next());
//            deleteOrder(t1, t2, t3);
//        }
//    }
//
//    private static void openDB(String host, String dbname, String user, String pwd) {
//        try {
//            //
//            Class.forName("org.postgresql.Driver");
//        } catch (Exception e) {
//            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
//            System.exit(1);
//        }
//
//        String url = "jdbc:postgresql://" + host + "/" + dbname;
//        Properties props = new Properties();
//        props.setProperty("user", user);
//        props.setProperty("password", pwd);
//        try {
//            con = DriverManager.getConnection(url, props);
//            if (verbose) {
//                System.out.println("Successfully connected to the database "
//                        + dbname + " as " + user);
//            }
//            con.setAutoCommit(false);
//        } catch (SQLException e) {
//            System.err.println("Database connection failed");
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//
//    }
//
//    private static void closeDB() {
//        if (con != null) {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                con.close();
//                con = null;
//            } catch (Exception ignored) {
//            }
//        }
//    }
//}
