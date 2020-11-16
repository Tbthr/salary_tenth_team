package com.salary.mapper;

import com.salary.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Department record);

    Department selectByPrimaryKey(String id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
}