package com.song.test.utils;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

/** 数据库池工具类 **/
public class DpcpUtils {
    private static BasicDataSource dataSource = new BasicDataSource();
    private static PreparedStatement ps = null;

    static {
        try {
            // 连接数据库基础信息
            // Mac
//           dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8&useSSL=false");
//           dataSource.setPassword("12345678");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8");
            dataSource.setUsername("root");
            dataSource.setPassword("123321");

            // 连接池配置信息
            /** 初始化连接数 **/
            dataSource.setInitialSize(3);
            /**  最小空闲连接数  **/
            dataSource.setMinIdle(3);
            /**  最大空闲连接数  **/
            dataSource.setMaxIdle(3);
            /**  最大连接数  **/
            dataSource.setMaxActive(3);

            // 连接池借出和客户端返回连接检查配置
            /**  归还连接前检查连接有效性  **/
            dataSource.setTestOnReturn(false);
            /**  池子借出连接前检查连接有效性  **/
            dataSource.setTestOnBorrow(false);
            /**  最大等待时间  **/
            dataSource.setMaxWait(2000);

            /**  连接池支持预编译  **/
            dataSource.setPoolPreparedStatements(true);

        }catch (Exception e){

        }
    }

}
