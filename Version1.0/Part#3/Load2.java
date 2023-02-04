import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

public class Load2 {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        long start;
        long end;
        Properties prop = new Properties();
        prop.put("host", "localhost");
        prop.put("database", "project");
        Properties props = new Properties();
        props.put("user", "postgres");
        props.put("password", "Rsml07230827z");
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        CopyManager copyManager = null;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, props);
            copyManager = new CopyManager((BaseConnection) connection);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }


        BufferedReader infile =
                new BufferedReader(new FileReader(
                        "javaPart/src/contract_info.csv"
                ));

        String line;
        String[] parts;
        infile.readLine();
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


        File writeName = new File("javaPart/src/source_copy/sc.txt");
        File writeName1 = new File("javaPart/src/source_copy/r.txt");
        File writeName2 = new File("javaPart/src/source_copy/c.txt");
        File writeName3 = new File("javaPart/src/source_copy/s.txt");
        File writeName4 = new File("javaPart/src/source_copy/co.txt");
        File writeName5 = new File("javaPart/src/source_copy/p.txt");
        File writeName6 = new File("javaPart/src/source_copy/o.txt");

        if(!writeName.exists()) writeName.createNewFile();
        if(!writeName1.exists()) writeName1.createNewFile();
        if(!writeName2.exists()) writeName2.createNewFile();
        if(!writeName3.exists()) writeName3.createNewFile();
        if(!writeName4.exists()) writeName4.createNewFile();
        if(!writeName5.exists()) writeName5.createNewFile();
        if(!writeName6.exists()) writeName6.createNewFile();

        FileWriter writer = new FileWriter(writeName);
        BufferedWriter out = new BufferedWriter(writer);

        FileWriter writer1 = new FileWriter(writeName1);
        BufferedWriter out1 = new BufferedWriter(writer1);

        FileWriter writer2 = new FileWriter(writeName2);
        BufferedWriter out2 = new BufferedWriter(writer2);

        FileWriter writer3 = new FileWriter(writeName3);
        BufferedWriter out3 = new BufferedWriter(writer3);

        FileWriter writer4 = new FileWriter(writeName4);
        BufferedWriter out4 = new BufferedWriter(writer4);

        FileWriter writer5 = new FileWriter(writeName5);
        BufferedWriter out5 = new BufferedWriter(writer5);

        FileWriter writer6 = new FileWriter(writeName6);
        BufferedWriter out6 = new BufferedWriter(writer6);

        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder1 = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        StringBuilder stringBuilder3 = new StringBuilder();
        StringBuilder stringBuilder4 = new StringBuilder();
        StringBuilder stringBuilder5 = new StringBuilder();
        StringBuilder stringBuilder6 = new StringBuilder();

        while ((line = infile.readLine()) != null) {
            parts = line.split(",");
            if (parts.length > 1) {
                if (!supplyCenterUnique.containsKey(parts[2])) {
                    supplyCenterUnique.put(parts[2], supplyCenter_int++);
                    stringBuilder.append(parts[2]).append(",").append(parts[14]).append("\n");
                }

                if (!relationUnique.containsKey(parts[5])) {
                    relationUnique.put(parts[5], relation_int++);
                    stringBuilder1.append(parts[5]).append(",").append(supplyCenterUnique.get(parts[2])).append("\n");
                }

                if (!customerUnique.containsKey(parts[1])) {
                    customerUnique.put(parts[1], customer_int++);
                    stringBuilder2.append(parts[1]).append(",").append(parts[3]).append(",").append(parts[4]).append(",").append(relationUnique.get(parts[5])).append("\n");
                }

                if (!salesmanUnique.containsKey(Integer.parseInt(parts[16]))) {
                    salesmanUnique.put(Integer.parseInt(parts[16]), salesman_int++);
                    stringBuilder3.append(Integer.parseInt(parts[16])).append(",").append(parts[15]).append(",").append(parts[17]).append(",").append(Integer.parseInt(parts[18])).append(",").append(Long.parseLong(parts[19])).append(",").append(supplyCenterUnique.get(parts[2])).append("\n");
                }


                Date contract_date;
                if (parts[11].contains("/")) {
                    contract_date = Date.valueOf(parts[11].replace("/", "-"));
                } else {
                    contract_date = Date.valueOf(parts[11]);
                }
                if (!contractUnique.containsKey(parts[0])) {
                    contractUnique.put(parts[0], contract_int++);
                    stringBuilder4.append(parts[0]).append(",").append(contract_date).append(",").append(customerUnique.get(parts[1])).append("\n");
                }

                if (!productUnique.containsKey(parts[6])) {
                    productUnique.put(parts[6], product_int++);
                    stringBuilder5.append(parts[8]).append(",").append(parts[6]).append(",").append(parts[7]).append(",").append(Integer.parseInt(parts[9])).append("\n");
                }

                Date lodgement_date;
                try {
                    lodgement_date = Date.valueOf(parts[13]);
                } catch (Exception e) {
                    lodgement_date = null;
                }

                stringBuilder6.append(contractUnique.get(parts[0])).append(",").append(productUnique.get(parts[6])).append(",").append(salesmanUnique.get(Integer.parseInt(parts[16]))).append(",").append(Integer.parseInt(parts[10])).append(",").append(Date.valueOf(parts[12])).append(",").append(lodgement_date).append("\n");
            }
        }
        out.write(stringBuilder.toString());
        out.close();
        out1.write(stringBuilder1.toString());
        out1.close();
        out2.write(stringBuilder2.toString());
        out2.close();
        out3.write(stringBuilder3.toString());
        out3.close();
        out4.write(stringBuilder4.toString());
        out4.close();
        out5.write(stringBuilder5.toString());
        out5.close();
        out6.write(stringBuilder6.toString());
        out6.close();

        Statement statement =connection.createStatement();
        statement.execute("alter table relation drop constraint relation_supply_center_id_fkey cascade ;\n" +
                "alter table salesman drop constraint salesman_supply_center_id_fkey cascade ;\n" +
                "alter table customer drop constraint customer_industry_id_fkey cascade ;\n" +
                "alter table contract drop constraint contract_customer_id_fkey cascade ;\n" +
                "alter table orders drop constraint orders_contract_number_fkey cascade ;\n" +
                "alter table orders drop constraint orders_product_code_fkey cascade ;\n" +
                "alter table orders drop constraint orders_salesman_number_fkey cascade ;\n" +
                "alter sequence contract_contract_id_seq restart with 1;\n" +
                "alter sequence orders_order_id_seq restart with 1;\n" +
                "alter sequence product_product_id_seq restart with 1;\n" +
                "alter sequence salesman_salesman_id_seq restart with 1;\n" +
                "alter sequence customer_customer_id_seq restart with 1;\n" +
                "alter sequence supplycenter_supply_center_id_seq restart with 1;\n" +
                "alter sequence relation_industry_id_seq restart with 1;\n" +
                "truncate table relation cascade;\n" +
                "truncate table customer cascade;\n" +
                "truncate table product cascade;\n" +
                "truncate table contract cascade;\n" +
                "truncate table salesman cascade;\n" +
                "truncate table orders cascade;\n" +
                "truncate table supplyCenter cascade;\n" +
                "drop index product_index;\n" +
                "drop index supplyCenter_index;\n" +
                "drop index relation_index;\n" +
                "drop index customer_index;\n" +
                "drop index salesman_index;\n" +
                "drop index contract_index;\n" +
                "drop index orders_index;");


        start = System.currentTimeMillis();
        FileInputStream fileInputStream = new FileInputStream(writeName);
        FileInputStream fileInputStream1 = new FileInputStream(writeName1);
        FileInputStream fileInputStream2 = new FileInputStream(writeName2);
        FileInputStream fileInputStream3 = new FileInputStream(writeName3);
        FileInputStream fileInputStream4 = new FileInputStream(writeName4);
        FileInputStream fileInputStream5 = new FileInputStream(writeName5);
        FileInputStream fileInputStream6 = new FileInputStream(writeName6);
        copyManager.copyIn("COPY supplyCenter(supply_center,manager) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream);
        copyManager.copyIn("COPY relation(industry,supply_center_id) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream1);
        copyManager.copyIn("COPY customer(client_enterprise_name,country,city,industry_id) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream2);
        copyManager.copyIn("COPY salesman(salesman_number,salesman_name,gender,age,phone_number,supply_center_id) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream3);
        copyManager.copyIn("COPY contract(contract_number,contract_date,customer_id) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream4);
        copyManager.copyIn("COPY product(product_model,product_code,product_name,price) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream5);
        copyManager.copyIn("COPY orders(contract_number,product_code,salesman_number,product_num,estimated_delivery_date,lodgement_date) FROM STDIN delimiter as ',' NULL as 'null'", fileInputStream6);
        end = System.currentTimeMillis();

        int cnt = 50000;
        System.out.println(cnt + " records successfully loaded");
        System.out.println("Loading speed : "
                + (cnt * 1000) / (end - start)
                + " records/s");

        Statement statement2 =connection.createStatement();
        statement2.execute("alter table relation add foreign key (supply_center_id) references supplyCenter(supply_center_id);\n" +
                "alter table salesman add foreign key (supply_center_id) references supplyCenter(supply_center_id);\n" +
                "alter table customer add foreign key (industry_id) references relation(industry_id);\n" +
                "alter table contract add foreign key (customer_id) references customer(customer_id);\n" +
                "alter table orders add foreign key (salesman_number) references salesman(salesman_id);\n" +
                "alter table orders add foreign key (contract_number) references contract(contract_id);\n" +
                "alter table orders add foreign key (product_code) references product(product_id);"+
                "create index product_index on product(product_id);\n" +
                "create index supplyCenter_index on supplyCenter(supply_center_id);\n" +
                "create index relation_index on relation(industry_id);\n" +
                "create index customer_index on customer(customer_id);\n" +
                "create index salesman_index on salesman(salesman_id);\n" +
                "create index contract_index on contract(contract_id);\n" +
                "create index orders_index on orders(order_id);");

    }
}
