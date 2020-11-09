package com.salary.mapper;

import com.salary.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(String departId);

    int insert(Department record);

    Department selectByPrimaryKey(String departId);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}