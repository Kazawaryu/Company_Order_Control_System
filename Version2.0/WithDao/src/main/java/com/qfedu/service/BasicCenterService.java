package com.qfedu.service;

import com.qfedu.model.center;

public interface BasicCenterService {
    public boolean add(center center);
    public boolean delete(int id);
    public center query(int id);
    public boolean change(center center);
}