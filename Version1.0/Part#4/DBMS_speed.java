import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

class QWriter implements Closeable {
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public void print(Object object) {
        try {
            writer.write(object.toString());
        } catch (IOException e) {
            return;
        }
    }

    public void println(Object object) {
        try {
            writer.write(object.toString());
            writer.write("\n");
        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            return;
        }
    }
}

public class DBMS_speed {
    static Connection con;
    static Statement sql;
    static ResultSet res;
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

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        QWriter out = new QWriter();
        Properties defprop = new Properties();
        defprop.put("host", "localhost");
        defprop.put("user", "checker");
        defprop.put("password", "123456");
        defprop.put("database", "Proj_01");
        Properties prop = new Properties(defprop);

        openDB(prop.getProperty("host"), prop.getProperty("database"),
                prop.getProperty("user"), prop.getProperty("password"));

        try {
            System.out.println("Input 1 to select, 2 to delete, 3 to update, 4 to insert, -1 to quit.");
            int operation = in.nextInt();
            if (operation == 9/*Select all ,not very comparable ,just to get information*/) {
                sql = con.createStatement();
                res = sql.executeQuery("select * from orders where order_id <= " + 50000);

                PreparedStatement stm_contract = con.prepareStatement("select * from contract where contract_id = " + "?");
                PreparedStatement stm_customer = con.prepareStatement("select * from customer where customer_id = " + "?");
                PreparedStatement stm_relation = con.prepareStatement("select * from relation where industry = " + "?");
                PreparedStatement stm_product = con.prepareStatement("select * from product where product_id = " + "?");
                PreparedStatement stm_salesman = con.prepareStatement("select * from salesman where salesman_id = " + "?");

                int order_id;
                int contract_id;
                int product_id;
                int salesman_id;
                int product_num;
                String estimated_date;
                String contract_number;
                String contract_date;
                int customer_id;
                String client_enterprise_name;
                String country;
                String city;
                String industry;
                String supply_center;
                String manager;
                String product_code;
                String product_model;
                String product_name;
                int price;
                int salesman_number;
                String salesman_name;
                String gender;
                int age;
                String phone_number;

                long start = System.currentTimeMillis();

                while (res.next()) {
                    ResultSet subRes;
                    order_id = res.getInt(1);
                    contract_id = res.getInt(2);
                    product_id = res.getInt(3);
                    salesman_id = res.getInt(4);
                    product_num = res.getInt(5);
                    estimated_date = res.getString(6);

                    stm_contract.setInt(1, contract_id);
                    subRes = stm_contract.executeQuery();
                    subRes.next();
                    contract_number = subRes.getString(2);
                    contract_date = subRes.getString(3);

                    customer_id = subRes.getInt(4);
                    stm_customer.setInt(1, customer_id);
                    subRes = stm_customer.executeQuery();
                    subRes.next();
                    client_enterprise_name = subRes.getString(2);
                    country = subRes.getString(3);
                    city = subRes.getString(4);
                    industry = subRes.getString(5);

                    stm_relation.setString(1, industry);
                    subRes = stm_relation.executeQuery();
                    subRes.next();
                    supply_center = subRes.getString(2);
                    manager = subRes.getString(3);

                    stm_product.setInt(1, product_id);
                    subRes = stm_product.executeQuery();
                    subRes.next();
                    product_code = subRes.getString(2);
                    product_model = subRes.getString(3);
                    product_name = subRes.getString(4);
                    price = subRes.getInt(5);

                    stm_salesman.setInt(1, salesman_id);
                    subRes = stm_salesman.executeQuery();
                    subRes.next();
                    salesman_number = subRes.getInt(2);
                    salesman_name = subRes.getString(3);
                    gender = subRes.getString(4);
                    age = subRes.getInt(5);
                    phone_number = subRes.getString(6);

                    out.println(order_id + " " + contract_number + " " + supply_center + " " + country + " " + city + " " + industry + " " + product_code + " " + product_name + " " + product_model + " " + price + " " + product_num + " " + contract_date + " " + estimated_date + " " + manager + " " + salesman_name + " " + salesman_number + " " + gender + " " + age + " " + phone_number);
                    subRes.close();
                }

                res.close();

                long end = System.currentTimeMillis();
                long time = (end - start);
                int ave = (int) (250000 * 1000 / time);
                out.println("程序运行时间：" + (end - start) + "ms");
                out.print("共执行250000条SQL查询语句，平均每秒执行 " + ave + " 条语句");
                out.close();
            } else if (operation == 1/*Select*/) {
                PreparedStatement stm = con.prepareStatement("select * from orders where order_id > ? and order_id < ?");

                System.out.print("From ");
                int begin = in.nextInt();
                System.out.print(" to ");
                int over = in.nextInt();
                long start = System.currentTimeMillis();

                stm.setInt(1, begin);
                stm.setInt(2, over);

                res = stm.executeQuery();
                long end = System.currentTimeMillis();
                int cnt = 1;
                while (res.next()) {
                    cnt++;
                    int id = res.getInt(1);
                    int con_id = res.getInt(2);
                    int pro_id = res.getInt(3);
                    int sal_id = res.getInt(4);
                    int pro_num = res.getInt(5);
                    Date date_1 = res.getDate(6);
                    Date date_2 = res.getDate(7);
                    out.println(id + " " + con_id + " " + pro_id + " " + sal_id + " " + pro_num + " " + date_1 + " " + date_2);
                }

                con.commit();


                out.println("程序运行时间：" + (end - start) + "ms");
                int ave = (int) ((over - begin + 1) * 1000 / (end - start));
                out.print("共执行 " + (over - begin + 1) + " 条SQL查询语句，平均每秒执行 " + ave + " 条语句");
                out.close();
                closeDB();
            } else if (operation == 2/*Delete*/) {
                System.out.println("Input 1 to batch delete ,2 to select delete");
                int choose = in.nextInt();

                if (choose==1) {
                    PreparedStatement stm = con.prepareStatement("delete from orders where order_id = " + "?");
                    System.out.print("From ");
                    int begin = in.nextInt();
                    System.out.print(" to ");
                    int over = in.nextInt();

                    long start = System.currentTimeMillis();

                    int SIZE = 500;
                    int cnt = 1;
                    for (int i = begin; i < over; i++) {
                        stm.setInt(1, i);
                        stm.addBatch();
                        if (cnt++ % SIZE == 0) {
                            stm.executeBatch();
                            stm.clearBatch();
                        }
                    }

                    stm.executeBatch();
                    con.commit();

                    long end = System.currentTimeMillis();
                    System.out.println("程序运行时间：" + (end - start) + "ms");
                    int ave = (int) ((over - begin) * 1000 / (end - start));
                    System.out.print("共执行 " + (over - begin) + " 条SQL删除语句，平均每秒执行 " + ave + " 条语句");

                    closeDB();
                }else if (choose == 2){
                    System.out.println("Input deleting lines: ");
                    int l = in.nextInt();
                    int[] lines = new int[l];
                    for (int i = 0; i < l; i++) {
                        lines[i] = in.nextInt();
                    }

                    PreparedStatement stm = con.prepareStatement("delete from orders where order_id = " + "?");

                    int SIZE = 500;
                    long start = System.currentTimeMillis();
                    for (int i = 0; i < l; i++) {
                        stm.setInt(1,lines[i]);
                        stm.addBatch();
                        if (i % SIZE == 0) {
                            stm.executeBatch();
                            stm.clearBatch();
                        }
                    }

                    stm.executeBatch();
                    con.commit();

                    long end = System.currentTimeMillis();
                    System.out.println("程序运行时间：" + (end - start) + "ms");
                    int ave = (int) ((l) * 1000 / (end - start));
                    System.out.print("共执行 " + (l) + " 条SQL删除语句，平均每秒执行 " + ave + " 条语句");

                    closeDB();
                }
            } else if (operation == 3/*Update*/) {
                PreparedStatement stm = con.prepareStatement("update orders set contract_number = ?,product_code = ?,salesman_number = ?,product_num = ?,estimated_delivery_date=? where order_id = ?");

                Random random = new Random();
                int contract_id = 5000;
                int product_id = 325;
                int salesman_id = 990;
                int product_num;
                String date;

                System.out.print("From ");
                int begin = in.nextInt();
                System.out.print(" to ");
                int over = in.nextInt();

                int con_id;
                int pro_id;
                int sal_id;

                long start = System.currentTimeMillis();

                int SIZE = 500;
                int cnt = 0;
                for (int i = begin; i < over; i++) {

                    con_id = random.nextInt((contract_id - 1) + 1) + 1;
                    pro_id = random.nextInt((product_id - 1) + 1) + 1;
                    sal_id = random.nextInt((salesman_id - 1) + 1) + 1;
                    product_num = random.nextInt((10000 - 1) + 1) + 1;
                    date = String.valueOf(random.nextInt((2021 - 2000) + 1) + 2000) + "-" + (random.nextInt((12 - 1) + 1) + 1) + "-" + (random.nextInt((28 - 1) + 1) + 1);

                    stm.setInt(1, con_id);
                    stm.setInt(2, pro_id);
                    stm.setInt(3, sal_id);
                    stm.setInt(4, product_num);
                    stm.setDate(5, Date.valueOf(date));
                    stm.setInt(6, i);

                    stm.addBatch();
                    if (cnt++ % SIZE == 0) {
                        stm.executeBatch();
                        stm.clearBatch();
                    }
                }

                stm.executeBatch();
                con.commit();

                long end = System.currentTimeMillis();
                System.out.println("程序运行时间：" + (end - start) + "ms");
                int ave = (int) ((over - begin) * 1000 / (end - start));
                System.out.print("共执行 " + (over - begin) + " 条SQL更新语句，平均每秒执行 " + ave + " 条语句");

                closeDB();
            } else if (operation == 4/*Insert*/) {
                PreparedStatement stm = con.prepareStatement("insert into orders(contract_number,product_code,salesman_number,product_num,estimated_delivery_date,lodgement_date)" + " values(?,?,?,?,?,?)");
                Random random = new Random();
                int contract_id = 5000;
                int product_id = 325;
                int salesman_id = 990;
                int product_num;
                String date;

                System.out.print("Count ");
                int count = in.nextInt();

                int con_id;
                int pro_id;
                int sal_id;

                long start = System.currentTimeMillis();

                int SIZE = 500;
                int cnt = 1;
                for (int i = 0; i < count; i++) {

                    con_id = random.nextInt((contract_id - 1) + 1) + 1;
                    pro_id = random.nextInt((product_id - 1) + 1) + 1;
                    sal_id = random.nextInt((salesman_id - 1) + 1) + 1;
                    product_num = random.nextInt((10000 - 1) + 1) + 1;
                    date = String.valueOf(random.nextInt((2021 - 2000) + 1) + 2000) + "-" + (random.nextInt((12 - 1) + 1) + 1) + "-" + (random.nextInt((28 - 1) + 1) + 1);

                    stm.setInt(1, con_id);
                    stm.setInt(2, pro_id);
                    stm.setInt(3, sal_id);
                    stm.setInt(4, product_num);
                    stm.setDate(5, Date.valueOf(date));
                    stm.setDate(6, Date.valueOf(date));

                    stm.addBatch();
                    if (cnt++ % SIZE == 0) {
                        stm.executeBatch();
                        stm.clearBatch();
                    }
                }

                stm.executeBatch();
                con.commit();

                long end = System.currentTimeMillis();
                System.out.println("程序运行时间：" + (end - start) + "ms");
                int ave = (int) ((count) * 1000 / (end - start));
                System.out.print("共执行 " + (count) + " 条SQL增添语句，平均每秒执行 " + ave + " 条语句");

                closeDB();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

