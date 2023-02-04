import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.sql.*;
import java.net.URL;

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

    private static boolean verbose = false;

    private static void openDB(String host, String dbname,
                               String user, String pwd) {
        try {
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

    private static void prepare(){
        try {
            stmt = con.prepareStatement("insert into contract(contract_number,contract_date,customer_id)"
                    + " values(?,?,?)");
            stmt1 = con.prepareStatement("insert into salesman(salesman_number,salesman_name,gender,age,phone_number,supply_center_id)"
                    + " values(?,?,?,?,?,?)");
            stmt2 = con.prepareStatement("insert into product(product_model,product_code,product_name,price)"
                    + " values(?,?,?,?)");
            stmt3 = con.prepareStatement("insert into relation(industry,supply_center_id)"
                    + " values(?,?)");
            stmt4 = con.prepareStatement("insert into customer(client_enterprise_name,country,city,industry_id)"
                    + " values(?,?,?,?)");
            stmt5 = con.prepareStatement("insert into orders(contract_number,product_code,salesman_number,product_num,estimated_delivery_date,lodgement_date)"
                    + " values(?,?,?,?,?,?)");
            stmt6 = con.prepareStatement("insert into supplyCenter(supply_center,manager)"
                    + " values(?,?)");
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.err.println("Insert statement failed");
            System.err.println(e.getMessage());
            closeDB();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        String fileName = null;
        boolean verbose = false;

        switch (args.length) {
            case 1:
                fileName = args[0];
                break;
            case 2:
                switch (args[0]) {
                    case "-v":
                        verbose = true;
                        break;
                    default:
                        System.err.println("Usage: java [-v] Loader filename");
                        System.exit(1);
                }
                fileName = args[1];
                break;
            default:
                System.err.println("Usage: java [-v] Loader filename");
                System.exit(1);
        }

        Properties prop = new Properties();
        prop.put("host", "localhost");
        prop.put("user", "postgres");
        prop.put("password", "Rsml07230827z");
        prop.put("database", "project");

        try (BufferedReader infile
                     = new BufferedReader(new FileReader(fileName))) {
            long start;
            long end;
            String line;
            String[] parts;

            Date contract_date;
            Date lodgement_date = null;

            int cnt = 0;
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            if (con != null) {
                Statement stmt0 = con.createStatement();
                stmt0.execute("drop index product_index;\n" +
                        "drop index supplyCenter_index;\n" +
                        "drop index relation_index;\n" +
                        "drop index customer_index;\n" +
                        "drop index salesman_index;\n" +
                        "drop index contract_index;\n" +
                        "drop index orders_index;");
                con.commit();
                stmt0.execute("alter table relation drop constraint relation_supply_center_id_fkey cascade");
                stmt0.execute("alter table salesman drop constraint salesman_supply_center_id_fkey cascade");
                stmt0.execute("alter table customer drop constraint customer_industry_id_fkey cascade");
                stmt0.execute("alter table contract drop constraint contract_customer_id_fkey cascade");
                stmt0.execute("alter table orders drop constraint orders_contract_number_fkey cascade");
                stmt0.execute("alter table orders drop constraint orders_product_code_fkey cascade");
                stmt0.execute("alter table orders drop constraint orders_salesman_number_fkey cascade");
                con.commit();
                stmt0.execute("truncate table relation cascade");
                stmt0.execute("truncate table customer cascade");
                stmt0.execute("truncate table product cascade");
                stmt0.execute("truncate table contract cascade");
                stmt0.execute("truncate table salesman cascade");
                stmt0.execute("truncate table orders cascade");
                stmt0.execute("truncate table supplyCenter cascade");
                con.commit();
                stmt0.execute("alter sequence contract_contract_id_seq restart with 1");
                stmt0.execute("alter sequence orders_order_id_seq restart with 1");
                stmt0.execute("alter sequence product_product_id_seq restart with 1");
                stmt0.execute("alter sequence salesman_salesman_id_seq restart with 1");
                stmt0.execute("alter sequence customer_customer_id_seq restart with 1");
                stmt0.execute("alter sequence supplycenter_supply_center_id_seq restart with 1");
                stmt0.execute("alter sequence relation_industry_id_seq restart with 1");
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

            int relation_int = 1;
            start = System.currentTimeMillis();
            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            prepare();

            infile.readLine();
            while ((line = infile.readLine()) != null) {
                parts = line.split(",");
                if (parts.length > 1) {
                    if (!supplyCenterUnique.containsKey(parts[2])) {
                        supplyCenterUnique.put(parts[2], supplyCenter_int++);
                        SupplyCenterLoadData(parts[2], parts[14]);
                    }

                    if (!relationUnique.containsKey(parts[5])) {
                        relationUnique.put(parts[5], relation_int++);
                        RelationLoadData(parts[5], supplyCenterUnique.get(parts[2]));
                    }

                    if (!customerUnique.containsKey(parts[1])) {
                        customerUnique.put(parts[1], customer_int++);
                        CustomerLoadData(parts[1], parts[3], parts[4], relationUnique.get(parts[5]));
                    }

                    if (!salesmanUnique.containsKey(Integer.parseInt(parts[16]))) {
                        salesmanUnique.put(Integer.parseInt(parts[16]), salesman_int++);
                        SalesmanLoadData(Integer.parseInt(parts[16]), parts[15], parts[17], Integer.parseInt(parts[18]), Long.parseLong(parts[19]), supplyCenterUnique.get(parts[2]));
                    }


                    if (parts[11].contains("/")) {
                        contract_date = Date.valueOf(parts[11].replace("/", "-"));
                    } else {
                        contract_date = Date.valueOf(parts[11]);
                    }
                    if (!contractUnique.containsKey(parts[0])) {
                        contractUnique.put(parts[0], contract_int++);
                        ContractLoadData(parts[0], contract_date, customerUnique.get(parts[1]));
                    }

                    if (!productUnique.containsKey(parts[6])) {
                        productUnique.put(parts[6], product_int++);
                        ProductLoadData(parts[8], parts[6], parts[7], Integer.parseInt(parts[9]));
                    }

                    try {
                        lodgement_date = Date.valueOf(parts[13]);
                    } catch (Exception e) {
                        lodgement_date = null;
                    }
                    OrdersLoadData(contractUnique.get(parts[0]),
                            productUnique.get(parts[6]),
                            salesmanUnique.get(Integer.parseInt(parts[16])),
                            Integer.parseInt(parts[10]),
                            Date.valueOf(parts[12]),
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

            openDB(prop.getProperty("host"), prop.getProperty("database"),
                    prop.getProperty("user"), prop.getProperty("password"));
            if (con != null) {
                Statement stmt0 = con.createStatement();
                stmt0.execute("alter table relation add foreign key (supply_center_id) references supplyCenter(supply_center_id);\n" +
                        "alter table salesman add foreign key (supply_center_id) references supplyCenter(supply_center_id);\n" +
                        "alter table customer add foreign key (industry_id) references relation(industry_id);\n" +
                        "alter table contract add foreign key (customer_id) references customer(customer_id);\n" +
                        "alter table orders add foreign key (salesman_number) references salesman(salesman_id);\n" +
                        "alter table orders add foreign key (contract_number) references contract(contract_id);\n" +
                        "alter table orders add foreign key (product_code) references product(product_id);");
                con.commit();
                stmt0.execute("create index product_index on product(product_id);\n" +
                        "create index supplyCenter_index on supplyCenter(supply_center_id);\n" +
                        "create index relation_index on relation(industry_id);\n" +
                        "create index customer_index on customer(customer_id);\n" +
                        "create index salesman_index on salesman(salesman_id);\n" +
                        "create index contract_index on contract(contract_id);\n" +
                        "create index orders_index on orders(order_id);");
                con.commit();
            }
            closeDB();



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

            } catch (Exception ignored) {
            }
            closeDB();
            System.exit(1);
        }
//        closeDB();
    }

    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt1 != null) {
                    stmt1.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt2 != null) {
                    stmt2.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt3 != null) {
                    stmt3.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt4 != null) {
                    stmt4.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt5 != null) {
                    stmt5.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (stmt6 != null) {
                    stmt6.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
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

    private static void RelationLoadData(String industry,  int supply_center_id)
            throws SQLException {
        if (con != null) {
            stmt3.setString(1, industry);
            stmt3.setInt(2, supply_center_id);
            stmt3.addBatch();
        }
    }

    private static void CustomerLoadData(String client_enterprise_name, String country, String city, int industry)
            throws SQLException {
        if (con != null) {
            stmt4.setString(1, client_enterprise_name);
            stmt4.setString(2, country);
            stmt4.setString(3, city);
            stmt4.setInt(4, industry);
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

}
