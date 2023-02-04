package com.qfedu.service.impl;

import com.qfedu.dao.*;
import com.qfedu.model.product;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;

public class stockInService2impl {


    public static void main(String[] args) throws IOException {
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

    public static void stockIn(int id, String supply_center, String product_model, int supply_staff,
                               String date, int purchase_price, int quantity) {
         SqlSession sqlSession = MyBatisUtil.getSqlSession();
         mixDAO mixDAO = sqlSession.getMapper(mixDAO.class);

         productDAO productDAO = sqlSession.getMapper(productDAO.class);
        int judge1 = mixDAO.selectCountABWhat("model", "model", product_model);
        int judge2 = mixDAO.selectCountABWhat("center", "name", supply_center);
        int judge3 = mixDAO.selectCountABWhatInt("staff", "number", supply_staff);
        int judge4 = mixDAO.statementP1(supply_staff);
        if (judge1 == 0 || judge2 == 0 || judge3 == 0 || judge4 == 0) {
            return;
        }
        String temp = mixDAO.selectFromWhere1("supply_center", "staff", "number", supply_staff);
        if (!temp.equals(supply_center)) {
            return;
        }
        int judge5 = mixDAO.selectFromWhere2(product_model, supply_center);
        if (judge5 == 0) {
            product product = new product(id, supply_center, product_model, supply_staff,
                    Date.valueOf(date.replace("/", "-")), purchase_price, quantity);
            productDAO.insertProduct(product);
        } else {
            int temp2 = mixDAO.selectFromWhere3(product_model, supply_center);
            quantity = quantity + temp2;
            int idx = mixDAO.selectFromWhere4(product_model, supply_center);
            product product = new product(idx, supply_center, product_model, supply_staff,
                    Date.valueOf(date.replace("/", "-")), purchase_price, quantity);
            productDAO.updateProduct(product);
        }
        sqlSession.commit();
        System.out.println("stockIn over");
    }
}
