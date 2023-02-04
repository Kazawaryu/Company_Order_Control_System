import com.qfedu.dao.*;
import com.qfedu.model.center;
import com.qfedu.service.impl.*;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.*;
import java.sql.SQLException;
import java.util.Scanner;


public class Controller {
    static stockInService2impl stockInService2impl = new stockInService2impl();
    static placeOrdersImpl3 placeOrdersImpl3 = new placeOrdersImpl3();
    static updatOrdersImpl4 updatOrdersImpl4 = new updatOrdersImpl4();
    static deleteOrdersServiceImpl5 deleteOrdersServiceImpl5 = new deleteOrdersServiceImpl5();
    static BasicCenterServiceImpl basicCenterService = new BasicCenterServiceImpl()
;
    public static void main(String[] args) throws IOException, SQLException {

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("0:全部导入\n1:stockIn\n2:placeOrder\n3:updateOrder\n4:deleteOrder\n5:basic");
//        int i = scanner.nextInt();
//        if (i == 0) {
            stockInRun();
            placeOrderRun();
            updateOrderRun();
            deleteOrderRun();
//        } else if (i == 1) {
//            stockInService2impl.stockIn(scanner.nextInt(), scanner.next(), scanner.next()
//                    , scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.nextInt());
//        } else if (i == 2) {
//            placeOrdersImpl3.placeOrder(
//                    scanner.next(),
//                    scanner.next(),
//                    scanner.next(),
//                    scanner.nextInt(),
//                    scanner.nextInt(),
//                    scanner.next(),
//                    scanner.next(),
//                    scanner.next(),
//                    scanner.nextInt(),
//                    scanner.next()
//            );
//        } else if (i == 3) {
//            updatOrdersImpl4.updateOrder(scanner.next(),
//                    scanner.next(),
//                    Integer.parseInt(scanner.next()),
//                    Integer.parseInt(scanner.next()),
//                    scanner.next(),
//                    scanner.next());
//        } else if (i == 4) {
//            deleteOrdersServiceImpl5.deleteOrder(scanner.next(),
//                    scanner.nextInt(),scanner.nextInt());
//        }else if(i == 5){
//
//            System.out.println("0:add\n1:delete\n2:change\n3:query");
//
//            int y = scanner.nextInt();
//            if(y == 0){
//                center center = new center(scanner.nextInt(), scanner.next());
//                basicCenterService.add(center);
//            }else if(y ==1){
//                int id = scanner.nextInt();
//                basicCenterService.delete(id);
//            }else if(y == 2){
//                center center = new center(scanner.nextInt(), scanner.next());
//                basicCenterService.change(center);
//            }else if(y == 3){
//                basicCenterService.query(scanner.nextInt());
//            }
//        }


    }


    public static void stockInRun() throws IOException, SQLException {
        String root = "testdata_final-5-21/in_stoke_test.csv";
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
                stockInService2impl.stockIn(Integer.parseInt(parts[0]),
                        parts[1].substring(1) + "," + parts[2].substring(0, parts[2].length() - 1),
                        parts[3],
                        Integer.parseInt(parts[4]),
                        parts[5],
                        Integer.parseInt(parts[6]),
                        Integer.parseInt(parts[7]));
            } else {
                stockInService2impl.stockIn(Integer.parseInt(parts[0]),
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
        String root = "testdata_final-5-21/task2_test_data_final_public.tsv";
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
            placeOrdersImpl3.placeOrder(
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
//BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("")));
//bufferedWriter.append("%12s")
        }
    }

    public static void updateOrderRun() throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(new File("testdata_final-5-21/update_final_test.tsv"));
//        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_update_test_data_publish.tsv"));
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            updatOrdersImpl4.updateOrder(scanner.next(),
                    scanner.next(),
                    Integer.parseInt(scanner.next()),
                    Integer.parseInt(scanner.next()),
                    scanner.next(),
                    scanner.next());
        }
    }


    public static void deleteOrderRun() throws FileNotFoundException, SQLException {
        Scanner scanner = new Scanner(new File("testdata_final-5-21/delete_final.tsv"));
//        Scanner scanner = new Scanner(new File("release-to-students/release-testcase1/task34_delete_test_data_publish.tsv"));
        scanner.nextLine();
        while (scanner.hasNext()) {
            String t1 = scanner.next();
            int t2 = Integer.parseInt(scanner.next());
            int t3 = Integer.parseInt(scanner.next());
            deleteOrdersServiceImpl5.deleteOrder(t1, t2, t3);
        }
    }


}
