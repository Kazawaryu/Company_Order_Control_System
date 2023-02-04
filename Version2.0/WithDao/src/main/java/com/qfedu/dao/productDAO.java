package com.qfedu.dao;


import com.qfedu.model.product;

import java.util.List;

public interface productDAO {


    public int insertProduct(product product);
    public int deleteProduct(int id);
    public int updateProduct(product product);
    public product queryProduct(int id);
    public List<product> listProduct();
    public int countProduct();
}