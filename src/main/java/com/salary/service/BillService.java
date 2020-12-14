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

    /**
     * 查询账单
     *
     * @param map  查询条件
     * @param page 当前页码
     * @param rows 每页条数
     * @return 筛选分页后的账单信息
     */
    public PageInfo<Bill> getInfo(HashMap<String, Object> map, int page, int rows) {
        PageHelper.startPage(page, rows);
        return new PageInfo<>(billMapper.selectAll(map));
    }

    /**
     * 查询账单
     *
     * @param map 查询条件
     * @return 筛选后的账单信息
     */
    public List<Bill> getInfoOrigin(HashMap<String, Object> map) {
        return billMapper.selectAll(map);
    }

    /**
     * 查询账单
     *
     * @param date   日期
     * @param userId 工号
     * @return 账单
     */
    public Bill getBillById(Date date, String userId) {
        return billMapper.selectById(date, userId);
    }

    /**
     * 插入账单
     *
     * @param bill 账单信息
     * @return 成功：1 失败：0
     */
    public int insertBill(Bill bill) {
        return billMapper.insert(bill);
    }

    /**
     * 更新账单
     *
     * @param bill 账单信息
     * @return 成功：1 失败：0
     */
    public int updateBill(Bill bill) {
        return billMapper.update(bill);
    }
}
