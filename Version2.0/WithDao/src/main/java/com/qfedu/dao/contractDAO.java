package com.qfedu.dao;

import com.qfedu.model.contract;

import java.util.List;

public interface contractDAO {

    public int insertContract(contract contract);
    public int deleteContract(String contract_num);
    public int updateContract(contract contract);
    public contract queryContract(String contract_num);
    public List<contract> listContract();
    public int countContract();
}