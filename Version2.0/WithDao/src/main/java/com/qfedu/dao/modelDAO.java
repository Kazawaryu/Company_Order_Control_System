package com.qfedu.dao;


import com.qfedu.model.model;

import java.util.List;

public interface modelDAO {

    public int insertModel(model model);
    public int deleteModel(int id);
    public int updateModel(model model);
    public model queryModel(int id);
    public List<model> listModel();
    public int countModel();
}