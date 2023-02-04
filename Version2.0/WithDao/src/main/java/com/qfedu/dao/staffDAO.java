package com.qfedu.dao;
import com.qfedu.model.staff;
import java.util.List;

public interface staffDAO {
    public int insertStaff(staff staff);
    public int deleteStaff(int id);
    public int updateStaff(staff staff);
    public staff queryStaff(int id);
    public List<staff> listStaff();
    public int countStaff();
}