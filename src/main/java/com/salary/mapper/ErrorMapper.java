package com.salary.mapper;

import com.salary.model.Error;

import java.util.List;

public interface ErrorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Error record);

    Error selectByPrimaryKey(Integer id);

    List<Error> selectAll();

    int updateByPrimaryKey(Error record);
}