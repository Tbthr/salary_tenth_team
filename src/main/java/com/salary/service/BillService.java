package com.salary.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.salary.mapper.BillMapper;
import com.salary.model.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BillService {
    @Resource
    private BillMapper billMapper;

    public PageInfo<Bill> getInfo(HashMap<String, Object> map, int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(billMapper.selectAll(map));
    }

    public List<Bill> getInfoOrigin(HashMap<String, Object> map) {
        return billMapper.selectAll(map);
    }

    public Bill getBillById(Date date, String userId) {
        return billMapper.selectById(date, userId);
    }

    public int insertBill(Bill bill) {
        return billMapper.insert(bill);
    }

    public int updateBill(Bill bill) {
        return billMapper.update(bill);
    }
}