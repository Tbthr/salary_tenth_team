package com.salary.mapper;

import com.salary.model.Bill;

import java.util.HashMap;
import java.util.List;

public interface BillMapper {
    int insert(Bill record);

    List<Bill> selectAll(HashMap<String, Object> map);
}