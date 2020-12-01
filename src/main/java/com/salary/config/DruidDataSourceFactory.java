package com.salary.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DruidDataSourceFactory extends PooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

    public Connection getDatabaseConnection() throws SQLException {
        return dataSource.getConnection();
    }
}