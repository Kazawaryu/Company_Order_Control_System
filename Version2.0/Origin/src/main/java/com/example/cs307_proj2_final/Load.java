//package com.example.proj2_gui_new;
//
//import org.postgresql.copy.CopyManager;
//import org.postgresql.core.BaseConnection;
//
//import java.io.*;
//import java.sql.*;
//import java.util.Properties;

//public class Load {
//    public static void main(String[] args) throws SQLException, IOException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (Exception e) {
//            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
//            System.exit(1);
//        }
//
//        Properties prop = new Properties();
//        prop.put("host", "localhost");
//        prop.put("database", "Proj_2");
//        Properties props = new Properties();
//        props.put("user", "checker");
//        props.put("password", "123456");
//        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
//        CopyManager copyManager = null;
//        Connection connection = null;
//
//        try {
//            connection = DriverManager.getConnection(url, props);
//            copyManager = new CopyManager((BaseConnection) connection);
//        } catch (SQLException e) {
//            System.err.println("Database connection failed");
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//
//        PreparedStatement preparedStatement = connection.prepareStatement("truncate table enterprise cascade ;\n" +
//                "truncate table model cascade ;\n" +
//                "truncate table salesman cascade ;\n" +
//                "truncate table supplycenter cascade ;");
//        preparedStatement.execute();
//        connection.commit();
//
//
//        BufferedReader infile =
//                new BufferedReader(new FileReader(
//                        "file/center.csv"
//                ));
//        BufferedReader infile1 =
//                new BufferedReader(new FileReader(
//                        "file/enterprise.csv"
//                ));
//        BufferedReader infile2 =
//                new BufferedReader(new FileReader(
//                        "file/staff.csv"
//                ));
//        String line;
//        String[] parts;
//        infile.readLine();
//        infile1.readLine();
//        infile2.readLine();
//
//        File writeName = new File("file/center1.csv");
//        File writeName1 = new File("file/enterprise1.csv");
//        File writeName2 = new File("file/staff1.csv");
//        if (!writeName.exists()) writeName.createNewFile();
//        if (!writeName1.exists()) writeName1.createNewFile();
//        if (!writeName2.exists()) writeName2.createNewFile();
//
//        FileWriter writer = new FileWriter(writeName);
//        BufferedWriter out = new BufferedWriter(writer);
//        FileWriter writer1 = new FileWriter(writeName1);
//        BufferedWriter out1 = new BufferedWriter(writer1);
//        FileWriter writer2 = new FileWriter(writeName2);
//        BufferedWriter out2 = new BufferedWriter(writer2);
//        StringBuilder sb = new StringBuilder();
//        StringBuilder sb1 = new StringBuilder();
//        StringBuilder sb2 = new StringBuilder();
//        while ((line = infile.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 2) {
//                sb.append(parts[0]).append("|").append(parts[1].substring(1)).append(",").append(parts[2].substring(0, parts[2].length() - 1)).append("\n");
//            } else {
//                sb.append(parts[0]).append("|").append(parts[1]).append("\n");
//            }
//        }
//        while ((line = infile1.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 6) {
//                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4].substring(1)).append(",").append(parts[5].substring(0, parts[5].length() - 1)).append("|").append(parts[6]).append("\n");
//            } else {
//                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("\n");
//            }
//        }
//        while ((line = infile2.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 8) {
//                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5].substring(1)).append(",").append(parts[6].substring(0, parts[6].length() - 1)).append("|").append(parts[7]).append("|").append(parts[8]).append("\n");
//            } else {
//                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("|").append(parts[6]).append("|").append(parts[7]).append("\n");
//            }
//        }
//        out.write(sb.toString());
//        out.close();
//        FileInputStream fileInputStream = new FileInputStream(writeName);
//        out1.write(sb1.toString());
//        out1.close();
//        FileInputStream fileInputStream1 = new FileInputStream(writeName1);
//        out2.write(sb2.toString());
//        out2.close();
//        FileInputStream fileInputStream3 = new FileInputStream(writeName2);
//        FileInputStream fileInputStream2 = new FileInputStream("file/model.csv");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2));
//        bufferedReader.readLine();
//
//        copyManager.copyIn("COPY center(id,name) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream);
//        copyManager.copyIn("COPY enterprise(id,name,country,city,supply_center,industry) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream1);
//        copyManager.copyIn("COPY model(id,number,model,name,unit_price) FROM STDIN delimiter as ',' NULL as 'null'", bufferedReader);
//        copyManager.copyIn("COPY staff(id,name,age,gender,number,supply_center,mobile_number,type) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream3);
//
//        connection.close();
//    }
//    public static void main() throws SQLException, IOException {
//        try {
//            Class.forName("org.postgresql.Driver");
//        } catch (Exception e) {
//            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
//            System.exit(1);
//        }
//
//        Properties prop = new Properties();
//        prop.put("host", "localhost");
//        prop.put("database", "proj2");
//        Properties props = new Properties();
//        props.put("user", "postgres");
//        props.put("password", "Rsml07230827z");
//        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
//        CopyManager copyManager = null;
//        Connection connection = null;
//
//        try {
//            connection = DriverManager.getConnection(url, props);
//            copyManager = new CopyManager((BaseConnection) connection);
//        } catch (SQLException e) {
//            System.err.println("Database connection failed");
//            System.err.println(e.getMessage());
//            System.exit(1);
//        }
//
//        PreparedStatement preparedStatement = connection.prepareStatement("truncate table enterprise cascade ;\n" +
//                "truncate table model cascade ;\n" +
//                "truncate table salesman cascade ;\n" +
//                "truncate table supplycenter cascade ;");
//        preparedStatement.execute();
//        connection.commit();
//
//
//        BufferedReader infile =
//                new BufferedReader(new FileReader(
//                        "file/center.csv"
//                ));
//        BufferedReader infile1 =
//                new BufferedReader(new FileReader(
//                        "file/enterprise.csv"
//                ));
//        BufferedReader infile2 =
//                new BufferedReader(new FileReader(
//                        "file/staff.csv"
//                ));
//        String line;
//        String[] parts;
//        infile.readLine();
//        infile1.readLine();
//        infile2.readLine();
//
//        File writeName = new File("file/center1.csv");
//        File writeName1 = new File("file/enterprise1.csv");
//        File writeName2 = new File("file/staff1.csv");
//        if (!writeName.exists()) writeName.createNewFile();
//        if (!writeName1.exists()) writeName1.createNewFile();
//        if (!writeName2.exists()) writeName2.createNewFile();
//
//        FileWriter writer = new FileWriter(writeName);
//        BufferedWriter out = new BufferedWriter(writer);
//        FileWriter writer1 = new FileWriter(writeName1);
//        BufferedWriter out1 = new BufferedWriter(writer1);
//        FileWriter writer2 = new FileWriter(writeName2);
//        BufferedWriter out2 = new BufferedWriter(writer2);
//        StringBuilder sb = new StringBuilder();
//        StringBuilder sb1 = new StringBuilder();
//        StringBuilder sb2 = new StringBuilder();
//        while ((line = infile.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 2) {
//                sb.append(parts[0]).append("|").append(parts[1].substring(1)).append(",").append(parts[2].substring(0, parts[2].length() - 1)).append("\n");
//            } else {
//                sb.append(parts[0]).append("|").append(parts[1]).append("\n");
//            }
//        }
//        while ((line = infile1.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 6) {
//                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4].substring(1)).append(",").append(parts[5].substring(0, parts[5].length() - 1)).append("|").append(parts[6]).append("\n");
//            } else {
//                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("\n");
//            }
//        }
//        while ((line = infile2.readLine()) != null) {
//            parts = line.split(",");
//            if (parts.length != 8) {
//                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5].substring(1)).append(",").append(parts[6].substring(0, parts[6].length() - 1)).append("|").append(parts[7]).append("|").append(parts[8]).append("\n");
//            } else {
//                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("|").append(parts[6]).append("|").append(parts[7]).append("\n");
//            }
//        }
//        out.write(sb.toString());
//        out.close();
//        FileInputStream fileInputStream = new FileInputStream(writeName);
//        out1.write(sb1.toString());
//        out1.close();
//        FileInputStream fileInputStream1 = new FileInputStream(writeName1);
//        out2.write(sb2.toString());
//        out2.close();
//        FileInputStream fileInputStream3 = new FileInputStream(writeName2);
//        FileInputStream fileInputStream2 = new FileInputStream("file/model.csv");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2));
//        bufferedReader.readLine();
//
//        copyManager.copyIn("COPY center(id,name) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream);
//        copyManager.copyIn("COPY enterprise(id,name,country,city,supply_center,industry) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream1);
//        copyManager.copyIn("COPY model(id,number,model,name,unit_price) FROM STDIN delimiter as ',' NULL as 'null'", bufferedReader);
//        copyManager.copyIn("COPY staff(id,name,age,gender,number,supply_center,mobile_number,type) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream3);
//
//        connection.close();
//    }
//}


package com.example.cs307_proj2_final;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;



public class Load {

    public static void main() throws SQLException, IOException {
        System.out.println("LOOOOOOOOOOOOOOOOOODDDDDDDDDDDDD");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        Properties prop = new Properties();
        prop.put("host", "localhost");
        prop.put("database", "Proj_02");
        Properties props = new Properties();
        props.put("user", "checker");
        props.put("password", "123456");
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        CopyManager copyManager = null;
        Connection connection0 = null;

        try {
            connection0 = DriverManager.getConnection(url, props);
            copyManager = new CopyManager((BaseConnection) connection0);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
//        Statement stmt0 = connection0.createStatement();
//        stmt0.execute("truncate table center cascade");
//        Statement stmt1 = connection0.createStatement();
//        stmt1.execute("truncate table contract cascade");
//        Statement stmt2 = connection0.createStatement();
//        stmt0.execute("truncate table enterprise cascade");
//        Statement stmt3 = connection0.createStatement();
//        stmt0.execute("truncate table model cascade");
//        Statement stmt4 = connection0.createStatement();
//        stmt0.execute("truncate table orders cascade");
//        Statement stmt5 = connection0.createStatement();
//        stmt0.execute("truncate table product cascade");
//        Statement stmt6 = connection0.createStatement();
//        stmt0.execute("truncate table staff cascade");

        connection0.close();

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
                        "file/center.csv"
                ));
        BufferedReader infile1 =
                new BufferedReader(new FileReader(
                        "file/enterprise.csv"
                ));
        BufferedReader infile2 =
                new BufferedReader(new FileReader(
                        "file/staff.csv"
                ));
        String line;
        String[] parts;
        infile.readLine();
        infile1.readLine();
        infile2.readLine();

        File writeName = new File("file/center1.csv");
        File writeName1 = new File("file/enterprise1.csv");
        File writeName2 = new File("file/staff1.csv");
        if (!writeName.exists()) writeName.createNewFile();
        if (!writeName1.exists()) writeName1.createNewFile();
        if (!writeName2.exists()) writeName2.createNewFile();

        FileWriter writer = new FileWriter(writeName);
        BufferedWriter out = new BufferedWriter(writer);
        FileWriter writer1 = new FileWriter(writeName1);
        BufferedWriter out1 = new BufferedWriter(writer1);
        FileWriter writer2 = new FileWriter(writeName2);
        BufferedWriter out2 = new BufferedWriter(writer2);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        while ((line = infile.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 2) {
                sb.append(parts[0]).append("|").append(parts[1].substring(1)).append(",").append(parts[2].substring(0, parts[2].length() - 1)).append("\n");
            } else {
                sb.append(parts[0]).append("|").append(parts[1]).append("\n");
            }
        }
        while ((line = infile1.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 6) {
                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4].substring(1)).append(",").append(parts[5].substring(0, parts[5].length() - 1)).append("|").append(parts[6]).append("\n");
            } else {
                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("\n");
            }
        }
        while ((line = infile2.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 8) {
                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5].substring(1)).append(",").append(parts[6].substring(0, parts[6].length() - 1)).append("|").append(parts[7]).append("|").append(parts[8]).append("\n");
            } else {
                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("|").append(parts[6]).append("|").append(parts[7]).append("\n");
            }
        }
        out.write(sb.toString());
        out.close();
        FileInputStream fileInputStream = new FileInputStream(writeName);
        out1.write(sb1.toString());
        out1.close();
        FileInputStream fileInputStream1 = new FileInputStream(writeName1);
        out2.write(sb2.toString());
        out2.close();
        FileInputStream fileInputStream3 = new FileInputStream(writeName2);
        FileInputStream fileInputStream2 = new FileInputStream("file/model.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2));
        bufferedReader.readLine();

        copyManager.copyIn("COPY center(id,name) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream);
        copyManager.copyIn("COPY enterprise(id,name,country,city,supply_center,industry) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream1);
        copyManager.copyIn("COPY model(id,number,model,name,unit_price) FROM STDIN delimiter as ',' NULL as 'null'", bufferedReader);
        copyManager.copyIn("COPY staff(id,name,age,gender,number,supply_center,mobile_number,type) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream3);

        connection.close();

    }

    public static void main(String args[]) throws SQLException, IOException {
        System.out.println("LOOOOOOOOOOOOOOOOOODDDDDDDDDDDDD");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }

        Properties prop = new Properties();
        prop.put("host", "localhost");
        prop.put("database", "Proj_02");
        Properties props = new Properties();
        props.put("user", "checker");
        props.put("password", "123456");
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        CopyManager copyManager = null;
        Connection connection0 = null;

        try {
            connection0 = DriverManager.getConnection(url, props);
            copyManager = new CopyManager((BaseConnection) connection0);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
        Statement stmt0 = connection0.createStatement();
        stmt0.execute("truncate table center cascade");
        Statement stmt1 = connection0.createStatement();
        stmt1.execute("truncate table contract cascade");
        Statement stmt2 = connection0.createStatement();
        stmt0.execute("truncate table enterprise cascade");
        Statement stmt3 = connection0.createStatement();
        stmt0.execute("truncate table model cascade");
        Statement stmt4 = connection0.createStatement();
        stmt0.execute("truncate table orders cascade");
        Statement stmt5 = connection0.createStatement();
        stmt0.execute("truncate table product cascade");
        Statement stmt6 = connection0.createStatement();
//        stmt0.execute("truncate table staff cascade");


System.out.println("11111111111111");
        connection0.close();
        System.out.println("11122222222222221111");





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
                        "file/center.csv"
                ));
        BufferedReader infile1 =
                new BufferedReader(new FileReader(
                        "file/enterprise.csv"
                ));
        BufferedReader infile2 =
                new BufferedReader(new FileReader(
                        "file/staff.csv"
                ));
        String line;
        String[] parts;
        infile.readLine();
        infile1.readLine();
        infile2.readLine();

        File writeName = new File("file/center1.csv");
        File writeName1 = new File("file/enterprise1.csv");
        File writeName2 = new File("file/staff1.csv");
        if (!writeName.exists()) writeName.createNewFile();
        if (!writeName1.exists()) writeName1.createNewFile();
        if (!writeName2.exists()) writeName2.createNewFile();

        FileWriter writer = new FileWriter(writeName);
        BufferedWriter out = new BufferedWriter(writer);
        FileWriter writer1 = new FileWriter(writeName1);
        BufferedWriter out1 = new BufferedWriter(writer1);
        FileWriter writer2 = new FileWriter(writeName2);
        BufferedWriter out2 = new BufferedWriter(writer2);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        while ((line = infile.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 2) {
                sb.append(parts[0]).append("|").append(parts[1].substring(1)).append(",").append(parts[2].substring(0, parts[2].length() - 1)).append("\n");
            } else {
                sb.append(parts[0]).append("|").append(parts[1]).append("\n");
            }
        }
        while ((line = infile1.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 6) {
                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4].substring(1)).append(",").append(parts[5].substring(0, parts[5].length() - 1)).append("|").append(parts[6]).append("\n");
            } else {
                sb1.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("\n");
            }
        }
        while ((line = infile2.readLine()) != null) {
            parts = line.split(",");
            if (parts.length != 8) {
                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5].substring(1)).append(",").append(parts[6].substring(0, parts[6].length() - 1)).append("|").append(parts[7]).append("|").append(parts[8]).append("\n");
            } else {
                sb2.append(parts[0]).append("|").append(parts[1]).append("|").append(parts[2]).append("|").append(parts[3]).append("|").append(parts[4]).append("|").append(parts[5]).append("|").append(parts[6]).append("|").append(parts[7]).append("\n");
            }
        }
        out.write(sb.toString());
        out.close();
        FileInputStream fileInputStream = new FileInputStream(writeName);
        out1.write(sb1.toString());
        out1.close();
        FileInputStream fileInputStream1 = new FileInputStream(writeName1);
        out2.write(sb2.toString());
        out2.close();
        FileInputStream fileInputStream3 = new FileInputStream(writeName2);
        FileInputStream fileInputStream2 = new FileInputStream("file/model.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream2));
        bufferedReader.readLine();

        copyManager.copyIn("COPY center(id,name) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream);
        copyManager.copyIn("COPY enterprise(id,name,country,city,supply_center,industry) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream1);
        copyManager.copyIn("COPY model(id,number,model,name,unit_price) FROM STDIN delimiter as ',' NULL as 'null'", bufferedReader);
        copyManager.copyIn("COPY staff(id,name,age,gender,number,supply_center,mobile_number,type) FROM STDIN delimiter as '|' NULL as 'null'", fileInputStream3);

        connection.close();
        System.out.println("Load successfully");
    }
}
