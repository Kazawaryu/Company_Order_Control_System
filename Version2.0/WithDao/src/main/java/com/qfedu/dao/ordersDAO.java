package com.qfedu.dao;


import com.qfedu.model.orders;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ordersDAO {
    //这个表格有serial，最后搞！！！

    public int insertOrders(orders orders);
    public int deleteOrders(int id);
    public int updateOrders(orders orders);
    public orders queryOrders(int id);
    public List<orders> listOrders();
    public int countOrders();
    public List<orders> queryStaffList(@Param("A") int A, @Param("B") String B);
    public int selectID(@Param("A") String A, @Param("B") String B,@Param("C") String C,@Param("D") int D);
}