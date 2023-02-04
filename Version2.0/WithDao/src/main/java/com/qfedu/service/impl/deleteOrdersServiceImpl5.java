package com.qfedu.service.impl;
import com.qfedu.dao.contractDAO;
import com.qfedu.dao.mixDAO;
import com.qfedu.dao.ordersDAO;
import com.qfedu.model.orders;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class deleteOrdersServiceImpl5 {
    static SqlSession sqlSession = MyBatisUtil.getSqlSession();
    static mixDAO mixDAO = sqlSession.getMapper(mixDAO.class);
    static ordersDAO ordersDAO = sqlSession.getMapper(ordersDAO.class);

    public static void deleteOrder(String contract, int salesman, int sequence){
        List<orders> list = ordersDAO.queryStaffList(salesman,contract);
        if (sequence > 0 && sequence <= list.size()) {
            int id = ordersDAO.selectID(list.get(sequence-1).getContract_num(),
                    list.get(sequence-1).getEnterprise(),
                    list.get(sequence-1).getProduct_model(),
                    list.get(sequence-1).getSalesman_num());

            orders orders = mixDAO.selectFromWhere13(contract,id);
            System.out.println(orders);
            int delete_quantity = orders.getQuantity();
            String product_model = orders.getProduct_model();
            String enterprise = orders.getEnterprise();
            String supply_center = mixDAO.selectFromWhere5("supply_center","enterprise","name",enterprise);
            int inventory = mixDAO.selectFromWhere14(supply_center,product_model);
            mixDAO.selectFromWhere8((delete_quantity + inventory),product_model,supply_center);
            int temp = orders.getSalesman_num();
            if (temp != salesman) {
                return;
            }
            mixDAO.deleteFromAnd2(contract,id);
        }
        sqlSession.commit();
        System.out.println("delete over");
    }
}
