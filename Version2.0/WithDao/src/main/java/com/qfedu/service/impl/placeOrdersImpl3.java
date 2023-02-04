package com.qfedu.service.impl;

import com.qfedu.dao.contractDAO;
import com.qfedu.dao.mixDAO;
import com.qfedu.dao.ordersDAO;
import com.qfedu.model.contract;
import com.qfedu.model.orders;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;

public class placeOrdersImpl3 {
    static SqlSession sqlSession = MyBatisUtil.getSqlSession();
    static mixDAO mixDAO = sqlSession.getMapper(mixDAO.class);
    static contractDAO contractDAO = sqlSession.getMapper(contractDAO.class);
    static ordersDAO ordersDAO = sqlSession.getMapper(ordersDAO.class);

    public static void placeOrder(String contract_num, String enterprise, String product_model,
                                  int quantity, int contract_manager, String d, String d1, String d2,
                                  int salesman_num, String contract_type) {
        Date contract_date = Date.valueOf(d.replace("/", "-"));
        Date estimated_delivery_date = Date.valueOf(d1.replace("/", "-"));
        Date lodgement_date = Date.valueOf(d2.replace("/", "-"));

        String supply_center = mixDAO.selectFromWhere5("supply_center", "enterprise", "name", enterprise);
        String supply_center2 = mixDAO.selectFromWhere1("supply_center", "staff", "number", contract_manager);
        if (supply_center2 == null || !supply_center2.equals(supply_center)) return;
        supply_center2 = mixDAO.selectFromWhere1("supply_center", "staff", "number", salesman_num);
        if (!supply_center2.equals(supply_center)) return;

        String staff_type = mixDAO.selectFromWhere1("type", "staff", "number", salesman_num);
        if (!staff_type.equals("Salesman")) return;

        int product_num_exist = mixDAO.selectFromWhere6(product_model, supply_center);
        if (product_num_exist == 0) return;
        int product_num = mixDAO.selectFromWhere7(product_model, supply_center);
        if (product_num < quantity) return;

        int whether_contract_exist = mixDAO.selectFromWhere9(contract_num);
        if (whether_contract_exist == 0) {
            contract contract = new contract(contract_num, enterprise, contract_manager, contract_date,
                    estimated_delivery_date, lodgement_date, contract_type);
            contractDAO.insertContract(contract);
        }
        int updateQuantity = product_num - quantity;
        mixDAO.selectFromWhere8(updateQuantity, product_model, supply_center);
        orders orders = new orders(contract_num, enterprise, product_model, quantity,
                contract_manager, contract_date, estimated_delivery_date, lodgement_date,
                salesman_num, contract_type);
        ordersDAO.insertOrders(orders);

        sqlSession.commit();
        System.out.println("place over");
    }
}
