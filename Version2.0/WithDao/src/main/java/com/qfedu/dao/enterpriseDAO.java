package com.qfedu.dao;
import com.qfedu.model.enterprise;

import java.util.List;

public interface enterpriseDAO {

    public int insertEnterprise(enterprise enterprise);
    public int deleteEnterprise(int id);
    public int updateEnterprise(enterprise enterprise);
    public enterprise queryEnterprise(int id);
    public List<enterprise> listEnterprise();
    public int countEnterprise();
}