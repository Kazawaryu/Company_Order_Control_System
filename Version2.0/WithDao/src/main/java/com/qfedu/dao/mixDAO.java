package com.qfedu.dao;

import com.qfedu.model.orders;
import com.qfedu.model.staff;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface mixDAO {
    public int selectCountABWhat(@Param("A") String A, @Param("B") String B, @Param("what") String what);

    public int selectCountABWhatInt(@Param("A") String A, @Param("B") String B, @Param("what") int what);

    public int statementP1(@Param("s_s") int s_s);

    public String selectFromWhere1(@Param("A") String A, @Param("B") String B, @Param("C") String C, @Param("what") int what);

    public int selectFromWhere2(@Param("A") String A, @Param("B") String B);

    public int selectFromWhere3(@Param("A") String A, @Param("B") String B);

    public int selectFromWhere4(@Param("A") String A, @Param("B") String B);

    public String selectFromWhere5(@Param("A") String A, @Param("B") String B, @Param("C") String C, @Param("what") String what);

    public int selectFromWhere6(@Param("A") String A, @Param("B") String B);

    public int selectFromWhere7(@Param("A") String A, @Param("B") String B);

    public int selectFromWhere8(@Param("A") int A, @Param("B") String B, @Param("C") String C);

    public int selectFromWhere9(@Param("A") String A);

    public int selectFromWhere10(@Param("A") String A, @Param("B") String B, @Param("C") String C, @Param("D") String D, @Param("E") String E);

    public int selectFromWhere11(@Param("A") String A, @Param("B") String B);

    public int selectFromWhere12(@Param("A") String A, @Param("B") String B, @Param("C") int C);

    public orders selectFromWhere13(@Param("A") String A, @Param("B") int B);

    public int selectFromWhere14(@Param("A") String A, @Param("B") String B);

    public int deleteFromAnd2(@Param("A") String A, @Param("B") int B);

    public int deleteFromAnd(@Param("A") String A, @Param("B") String B, @Param("C") int C);

    public int updateTemp(@Param("A") int A, @Param("B") Date B, @Param("C") Date C, @Param("D") String D, @Param("E") String E, @Param("F") int F);




//More:::::::::::::::::::::::::::::::::::::::::::::::::
public staff getAllStaffCount();








}