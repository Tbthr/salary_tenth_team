package com.salary.mapper;

import com.salary.model.Bill;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface BillMapper {
    int insert(Bill record);

    List<Bill> selectAll(HashMap<String, Object> map);
}