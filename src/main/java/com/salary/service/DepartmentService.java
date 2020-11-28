package com.salary.service;

import com.salary.mapper.DepartmentMapper;
import com.salary.model.Department;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    public List<Department> getAllDeparts() {
        return departmentMapper.selectAll();
    }
}
