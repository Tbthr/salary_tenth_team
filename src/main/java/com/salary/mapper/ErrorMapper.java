package com.salary.mapper;

import com.salary.model.Error;

import java.util.List;

public interface ErrorMapper {
    int deleteByPrimaryKey(Integer errorId);

    int insert(Error record);

    Error selectByPrimaryKey(Integer errorId);

    List<Error> selectAll();

    int updateByPrimaryKey(Error record);
}