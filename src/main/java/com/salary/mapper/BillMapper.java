package com.salary.mapper;

import com.salary.model.Bill;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BillMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    int insert(Bill record);

    Bill selectByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    List<Bill> selectAll();

    int updateByPrimaryKey(Bill record);
}