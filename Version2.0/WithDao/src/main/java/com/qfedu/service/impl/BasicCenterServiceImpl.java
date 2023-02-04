package com.qfedu.service.impl;

import com.qfedu.dao.centerDAO;
import com.qfedu.dao.contractDAO;
import com.qfedu.dao.mixDAO;
import com.qfedu.dao.ordersDAO;
import com.qfedu.model.center;
import com.qfedu.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

public class BasicCenterServiceImpl {

    SqlSession sqlSession = MyBatisUtil.getSqlSession();
    private centerDAO centerDAO = sqlSession.getMapper(centerDAO.class);

    public boolean add(center center) {
        boolean b = false;
        try {
            int i = centerDAO.insertCenter(center);
            b = i > 0;
        } catch (Exception ignored) {
        }
        sqlSession.commit();
        return b;
    }

    public boolean delete(int id) {
        boolean b = false;
        try {
            int i = centerDAO.deleteCenter(id);
            b = i > 0;
        } catch (Exception ignored) {
        }
        sqlSession.commit();
        return b;
    }

    public center query(int id) {
        center b = null;
        try {
            b = centerDAO.queryCenter(id);
        } catch (Exception ignored) {
        }
        sqlSession.commit();
        return b;
    }

    public boolean change(center center) {
        boolean b = false;
        try {
            int i = centerDAO.updateCenter(center);
            b = i > 0;
        } catch (Exception ignored) {
        }
        sqlSession.commit();
        return b;
    }
}
