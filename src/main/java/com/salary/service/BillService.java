package com.salary.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.salary.mapper.BillMapper;
import com.salary.model.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class BillService {
    @Resource
    private BillMapper billMapper;

    public PageInfo<Bill> getInfo(HashMap<String, Object> map, int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(billMapper.selectAll(map));
    }
}
