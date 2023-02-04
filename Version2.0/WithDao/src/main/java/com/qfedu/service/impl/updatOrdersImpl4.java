package com.qfedu.service.impl;

import com.qfedu.dao.centerDAO;
import com.qfedu.dao.mixDAO;
import com.qfedu.dao.productDAO;
import com.qfedu.dao.staffDAO;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;

public class updatOrdersImpl4 {
    static SqlSession sqlSession = MyBatisUtil.getSqlSession();
    static com.qfedu.dao.mixDAO mixDAO = sqlSession.getMapper(mixDAO.class);

    public static void updateOrder(String contract_num, String product_model, int salesman_num,
                                   int quantity, String a, String b) {
        Date estimated_delivery_date = Date.valueOf(a.replace("/", "-"));
        Date lodgement_date = Date.valueOf(b.replace("/", "-"));
        int judge1 = mixDAO.selectFromWhere10("orders", "contract_num", contract_num,
                "product_model", product_model);
        if (judge1 == 0) return;
        String supply_center = mixDAO.selectFromWhere1("supply_center", "staff", "number", salesman_num);
        int inventory = mixDAO.selectFromWhere11(product_model, supply_center);
        int origin_quantity = mixDAO.selectFromWhere12(contract_num, product_model, salesman_num);
        if ((quantity - origin_quantity) > inventory) {
            return;
        } else if (quantity == 0) {
            mixDAO.selectFromWhere8((inventory + origin_quantity), product_model, supply_center);
            mixDAO.deleteFromAnd(contract_num, product_model, salesman_num);
        } else {
            mixDAO.selectFromWhere8((inventory - quantity + origin_quantity), product_model, supply_center);
            mixDAO.updateTemp(quantity, estimated_delivery_date, lodgement_date,
                    contract_num, product_model, salesman_num);
        }
        sqlSession.commit();
        System.out.println("update over");
    }
}
