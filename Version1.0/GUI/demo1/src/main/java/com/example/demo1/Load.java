package com.example.demo1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class Load {
    private static final int BATCH_SIZE = 50001;
    private static URL propertyURL = Load.class
            .getResource("/loader.cnf");

    private static Connection con = null;
    private static PreparedStatement stmt = null;
    private static PreparedStatement stmt1 = null;
    private static PreparedStatement stmt2 = null;
    private static PreparedStatement stmt3 = null;
    private static PreparedStatement stmt4 = null;
    private static PreparedStatement stmt5 = null;
    private static PreparedStatement stmt6 = null;
    private static PreparedStatement stmtx = null;

    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
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

        relation1();
        customer1();
        contract1();
        salesman1();
        product1();
        orders1();
        supplyCenter1();

    }

    public static void salesman1() {
        try {
            stmt1 = con.prepareStatement("insert into salesman(salesman_number,salesman_name,gender,age,phone_number,supply_center_id)"
                    + " values(?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }

    }

    public static void contract1() {
        try {
            stmt = con.prepareStatement("insert into contract(contract_number,contract_date,customer_id)"
                    + " values(?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failedfcosiehfoiehfiew");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void supplyCenter1() {
        try {
            stmt6 = con.prepareStatement("insert into supplyCenter(supply_center,manager)"
                    + " values(?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failedfcosiehfoiehfiew");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void product1() {
        try {
            stmt2 = con.prepareStatement("insert into product(product_model,product_code,product_name,price)"
                    + " values(?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void relation1() {
        try {
            stmt3 = con.prepareStatement("insert into relation(industry,supply_center,manager,supply_center_id)"
                    + " values(?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void customer1() {
        try {
            stmt4 = con.prepareStatement("insert into customer(client_enterprise_name,country,city,industry)"
                    + " values(?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void orders1() {
        try {
            stmt5 = con.prepareStatement("insert into orders(contract_number,product_code,salesman_number,product_num,estimated_delivery_date,lodgement_date)"
                    + " values(?,?,?,?,?,?)");
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    private static void ContractLoadData(String contract_number, Date contract_date, int customer_id)
            throws SQLException {
        if (con != null) {
            stmt.setString(1, contract_number);
            stmt.setDate(2, contract_date);
            stmt.setInt(3, customer_id);
            stmt.addBatch();
        }
    }

    private static void SupplyCenterLoadData(String s1, String s2)
            throws SQLException {
        if (con != null) {
            stmt6.setString(1, s1);
            stmt6.setString(2, s2);
            stmt6.addBatch();
        }
    }

    private static void SalesmanLoadData(Integer salesman_number, String salesman_name, String gender, int age, long phone_number, int supply_center_id)
            throws SQLException {
        if (con != null) {
            stmt1.setInt(1, salesman_number);
            stmt1.setString(2, salesman_name);
            stmt1.setString(3, gender);
            stmt1.setInt(4, age);
            stmt1.setLong(5, phone_number);
            stmt1.setInt(6, supply_center_id);
            stmt1.addBatch();
        }
    }

    private static void ProductLoadData(String product_model, String product_code, String product_name, int price)
            throws SQLException {
        if (con != null) {
            stmt2.setString(1, product_model);
            stmt2.setString(2, product_code);
            stmt2.setString(3, product_name);
            stmt2.setInt(4, price);
            stmt2.addBatch();
        }
    }

    private static void RelationLoadData(String industry, String supply_center, String manager, int supply_center_id)
            throws SQLException {
        if (con != null) {
            stmt3.setString(1, industry);
            stmt3.setString(2, supply_center);
            stmt3.setString(3, manager);
            stmt3.setInt(4, supply_center_id);
            stmt3.addBatch();
        }
    }

    private static void CustomerLoadData(String client_enterprise_name, String country, String city, String industry)
            throws SQLException {
        if (con != null) {
            stmt4.setString(1, client_enterprise_name);
            stmt4.setString(2, country);
            stmt4.setString(3, city);
            stmt4.setString(4, industry);
            stmt4.addBatch();
        }
    }

    private static void OrdersLoadData(int contract_number, int product_code, int salesman_number, int product_num, Date estimated_delivery_date, Date lodgement_date)
            throws SQLException {
        if (con != null) {
            stmt5.setInt(1, contract_number);
            stmt5.setInt(2, product_code);
            stmt5.setInt(3, salesman_number);
            stmt5.setInt(4, product_num);
            stmt5.setDate(5, estimated_delivery_date);
            stmt5.setDate(6, lodgement_date);
            stmt5.addBatch();
        }
    }

    public void reflash() {
        String fileName = null;
        boolean verbose = false;

//        switch (args.length) {
//            case 1:
//                fileName = args[0];
//                break;
//            case 2:
//                switch (args[0]) {
//                    case "-v":
//                        verbose = true;
//                        break;
//                    default:
//                        System.err.println("Usage: java [-v] Loader filename");
//                        System.exit(1);
//                }
//                fileName = args[1];
//                break;
//            default:
//                System.err.println("Usage: java [-v] Loader filename");
//                System.exit(1);
//        }
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "Proj_01");


        Properties prop = new Properties(defprop);
        try (BufferedReader infile
                     = new BufferedReader(new FileReader("G:\\Java\\CS307\\contract_info.csv"))) {
            long start;
            long end;

            String line;
            String[] parts;

            //创建全部用得上的变量
            String contract_number;
            Date contract_date;
            Integer product_num;
            String product_model = null;
            String product_code = null;
            String product_name = null;
            Integer price = null;
            Date estimated_delivery_date = null;
            Date lodgement_date = null;
            Integer salesman_number;
            String salesman_name;
            String gender;
            Integer age;
            Long phone_number;
            String client_enterprise_name;
            String country;
            String city;
            //后面几个先等等


            int cnt = 0;
            //连接测试，如果连不上直接报错
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));

            if (con != null) {

                Statement stmt0 = con.createStatement();
                stmt0.execute("truncate table contract cascade");
                stmt0.execute("truncate table customer cascade");
                stmt0.execute("truncate table product cascade");
                stmt0.execute("truncate table relation cascade");
                stmt0.execute("truncate table salesman cascade");
                stmt0.execute("truncate table orders cascade");
                stmt0.execute("truncate table supplycenter cascade");
                con.commit();
                stmt0.execute("alter sequence contract_contract_id_seq restart with 1");
                stmt0.execute("alter sequence orders_order_id_seq restart with 1");
                stmt0.execute("alter sequence product_product_id_seq restart with 1");
                stmt0.execute("alter sequence salesman_salesman_id_seq restart with 1");
                stmt0.execute("alter sequence customer_customer_id_seq restart with 1");
                stmt0.execute("alter sequence supplycenter_supply_center_id_seq restart with 1");
                con.commit();

            }
            closeDB();

            HashMap<Integer, Integer> salesmanUnique = new HashMap<>();
            int salesman_int = 1;
            HashMap<String, Integer> contractUnique = new HashMap<>();
            int contract_int = 1;
            HashMap<String, Integer> productUnique = new HashMap<>();
            int product_int = 1;
            HashMap<String, Integer> relationUnique = new HashMap<>();
            HashMap<String, Integer> customerUnique = new HashMap<>();
            int customer_int = 1;
            HashMap<String, Integer> supplyCenterUnique = new HashMap<>();
            int supplyCenter_int = 1;
            start = System.currentTimeMillis();
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            String sql="alter sequence supplycenter_supply_center_id_seq restart with 1";
//            PreparedStatement psmt=con.prepareStatement(sql);
//            String temp ="contract_contract_id_seq";
////                psmt.setString(1,temp);
//            System.out.println(psmt.execute());
            infile.readLine();
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {
                    if (!supplyCenterUnique.containsKey(parts[2])) {
                        supplyCenterUnique.put(parts[2], supplyCenter_int++);
                        SupplyCenterLoadData(parts[2], parts[14]);
                    }


                    if (!relationUnique.containsKey(parts[5])) {
                        relationUnique.put(parts[5], 0);
                        RelationLoadData(parts[5], parts[2], parts[14], supplyCenterUnique.get(parts[2]));
                    }

                    if (!customerUnique.containsKey(parts[1])) {
                        customerUnique.put(parts[1], customer_int++);
                        CustomerLoadData(parts[1], parts[3], parts[4], parts[5]);
                    }

                    if (!salesmanUnique.containsKey(Integer.parseInt(parts[16]))) {
                        salesmanUnique.put(Integer.parseInt(parts[16]), salesman_int++);
                        salesman_number = Integer.parseInt(parts[16]);
                        salesman_name = parts[15];
                        gender = parts[17];
                        age = Integer.parseInt(parts[18]);
                        phone_number = Long.parseLong(parts[19]);
                        SalesmanLoadData(salesman_number, salesman_name, gender, age, phone_number, supplyCenterUnique.get(parts[2]));
                    }

                    contract_number = parts[0];
                    if (parts[11].contains("/")) {
                        contract_date = Date.valueOf(parts[11].replace("/", "-"));
                    } else {
                        contract_date = Date.valueOf(parts[11]);
                    }
                    if (!contractUnique.containsKey(contract_number)) {
                        contractUnique.put(contract_number, contract_int++);
                        ContractLoadData(contract_number, contract_date, customerUnique.get(parts[1]));
                    }

                    if (!productUnique.containsKey(parts[6])) {
                        productUnique.put(parts[6], product_int++);
                        product_model = parts[8];
                        product_code = parts[6];
                        product_name = parts[7];
                        price = Integer.parseInt(parts[9]);
                        ProductLoadData(product_model, product_code, product_name, price);
                    }


                    if (parts[12].contains("/")){
                        estimated_delivery_date = Date.valueOf(parts[12].replace("/","-"));
                    }else estimated_delivery_date = Date.valueOf(parts[12]);



                    try {
                        lodgement_date = Date.valueOf(parts[13]);
                    } catch (Exception e) {
                        lodgement_date = null;
                    }
                    OrdersLoadData(contractUnique.get(parts[0]),
                            productUnique.get(parts[6]),
//                            customerUnique.get(parts[1]),
                            salesmanUnique.get(Integer.parseInt(parts[16])),
                            Integer.parseInt(parts[10]),
                            estimated_delivery_date,
                            lodgement_date);

                    cnt++;

                    if (cnt % BATCH_SIZE == 0) {
                        stmt6.executeBatch();
                        stmt6.clearBatch();
                        stmt3.executeBatch();
                        stmt3.clearBatch();
                        stmt4.executeBatch();
                        stmt4.clearBatch();
                        stmt.executeBatch();
                        stmt.clearBatch();
                        stmt1.executeBatch();
                        stmt1.clearBatch();
                        stmt2.executeBatch();
                        stmt2.clearBatch();
                        stmt5.executeBatch();
                        stmt5.clearBatch();

                    }
                }
            }
            if (cnt % BATCH_SIZE != 0) {
                stmt6.executeBatch();
                stmt6.clearBatch();
                stmt3.executeBatch();
                stmt3.clearBatch();
                stmt4.executeBatch();
                stmt4.clearBatch();
                stmt1.executeBatch();
                stmt1.clearBatch();
                stmt2.executeBatch();
                stmt2.clearBatch();
                stmt.executeBatch();
                stmt.clearBatch();
                stmt5.executeBatch();
                stmt5.clearBatch();


            }
            con.commit();
            stmt6.close();
            stmt3.close();
            stmt4.close();
            stmt.close();
            stmt1.close();
            stmt2.close();
            stmt5.close();

            closeDB();
            end = System.currentTimeMillis();
            System.out.println(cnt + " records successfully loaded");
            System.out.println("Loading speed : "
                    + (cnt * 1000) / (end - start)
                    + " records/s");
        } catch (SQLException se) {
            System.err.println("SQL error: " + se.getMessage());
            try {
                con.rollback();
                stmt6.close();
                stmt.close();
                stmt1.close();
                stmt2.close();
                stmt3.close();
                stmt4.close();
                stmt5.close();
            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Fatal error: " + e.getMessage());
            try {
                con.rollback();
                stmt6.close();
                stmt.close();
                stmt1.close();
                stmt2.close();
                stmt3.close();
                stmt4.close();
                stmt5.close();

            } catch (Exception e2) {
            }
            closeDB();
            System.exit(1);
        }
        closeDB();
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
            try {
                if (stmt1 != null) {
                    stmt1.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
            try {
                if (stmt2 != null) {
                    stmt2.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
            try {
                if (stmt3 != null) {
                    stmt3.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
            try {
                if (stmt4 != null) {
                    stmt4.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
            try {
                if (stmt5 != null) {
                    stmt5.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
            try {
                if (stmt6 != null) {
                    stmt6.close();
                }
                con.close();
                con = null;
            } catch (Exception e) {
                // Forget about it
            }
        }
    }
}
