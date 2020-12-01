package com.salary.mapper;

import com.salary.model.Log;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    Log selectByPrimaryKey(Integer id);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);
}