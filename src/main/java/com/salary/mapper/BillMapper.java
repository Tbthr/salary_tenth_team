package com.salary.mapper;

import com.salary.model.Bill;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface BillMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    int insert(Bill record);

    Bill selectByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    List<Bill> selectAll();

    int updateByPrimaryKey(Bill record);
}