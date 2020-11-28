package com.salary.mapper;

import com.salary.model.BillItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BillItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BillItem record);

    BillItem selectByPrimaryKey(Integer id);

    List<BillItem> selectAll();

    int updateByPrimaryKey(BillItem record);
}