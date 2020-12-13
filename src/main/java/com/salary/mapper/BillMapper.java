package com.salary.mapper;

import com.salary.model.Bill;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Transactional
public interface BillMapper {
    int insert(Bill record);

    List<Bill> selectAll(HashMap<String, Object> map);

    @Select("select * from bill where date = #{date} and user_id = #{userId} limit 1")
    Bill selectById(@Param("date") Date date,@Param("userId") String userId);

    int update(Bill bill);
}