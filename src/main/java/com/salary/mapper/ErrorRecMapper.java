package com.salary.mapper;

import com.salary.model.ErrorRec;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ErrorRecMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    int insert(ErrorRec record);

    ErrorRec selectByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    List<ErrorRec> selectAll();

    int updateByPrimaryKey(ErrorRec record);
}