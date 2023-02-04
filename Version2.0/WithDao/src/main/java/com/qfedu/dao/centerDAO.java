package com.qfedu.dao;

import com.qfedu.model.center;

import java.util.List;

public interface centerDAO {

    public int insertCenter(center center);
    public int deleteCenter(int id);
    public int updateCenter(center center);
    public center queryCenter(int id);
    public List<center> listCenter();
    public int countCenter();
}