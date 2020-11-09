package com.salary.mapper;

import com.salary.model.ErrorRec;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ErrorRecMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    int insert(ErrorRec record);

    ErrorRec selectByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    List<ErrorRec> selectAll();

    int updateByPrimaryKey(ErrorRec record);
}