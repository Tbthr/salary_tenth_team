package com.salary.mapper;

import com.salary.model.Confirm;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface ConfirmMapper {
    int deleteByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    int insert(Confirm record);

    Confirm selectByPrimaryKey(@Param("date") Date date, @Param("userId") String userId);

    List<Confirm> selectAll();

    int updateByPrimaryKey(Confirm record);
}