package com.song.test.utils;

import com.song.test.model.AutoLog;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** 数据库池工具类 **/
public class DpcpUtils {
    private static BasicDataSource dataSource = null;
    private static PreparedStatement ps = null;

    static {
        try {
            /** 确保只有一个连接池  **/
            if(dataSource == null){
                // 连接数据库基础信息
                // Mac
                dataSource = new BasicDataSource();
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8&useSSL=false");
                dataSource.setUsername("root");
                dataSource.setPassword("12345678");

//                dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8");
//                dataSource.setPassword("123321");

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
            }

        }catch (Exception e){

        }
    }

    /**
     * @Title: getConnection
     * @Description: 从连接池中借connection
     * @param: []
     * @return: java.sql.Connection
     * @date 2020/4/1 8:26 下午
     **/
    private static synchronized Connection getConnection(){
        // 初始化返回值
        Connection con = null;
        try {
            // 从连接池中借用连接
            con = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * @Title: JdbcQuery
     * @Description: 执行查询
     * @param: [sql]
     * @return: void
     * @date 2020/4/1 8:28 下午
     **/
    public static List<Object>  dpcpQuery(String sql){
        ResultSet resultSet = null;
        Connection connection = null;
        List<Object> objectList = null;

        try {
            // 对sql进行预编译，参数化及存储
            connection = getConnection();
            ps = connection.prepareStatement(sql);
//            ps.setInt(1,1); // 可以进行预编译和存储

            //step4 执行sql进行查询
            resultSet = ps.executeQuery();
            objectList = handler(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // step6 关闭资源,采用栈原则，后进先出
            close(ps,resultSet,connection);
        }
        return objectList;
    }

    public static int dpcpUpdate(String sql){
        Connection connection = null;
        int rowCount= 0;

        try {
            /**  获取连接  **/
            connection = getConnection();
            /** 预编译sql，参数化并存储  **/
            ps = connection.prepareStatement(sql);
            /**  执行语句  **/
            rowCount = ps.executeUpdate();
        }catch (Exception e){
            close(ps,null,connection);
        }
        return rowCount;
    }


    /**
     * @Title: handler
     * @Description: 批量处理insert，update，delete sql。
     * @param: [rs]
     * @return: java.util.List<java.lang.Object>
     * @date 2020/4/1 11:05 下午
     **/
    public static int[] dpcpBatchUpdate(List<AutoLog> list,String sql){
        int[] rowCount = null;
        Connection connection = null;
        /** 遍历集合  **/
        try {
            /**  连接池借连接，获取ps **/
            connection = getConnection();
            ps = connection.prepareStatement(sql);
            for (AutoLog autoLog : list) {
                // 动态sql获取数据
                ps.setString(1,autoLog.getTestCase());
                ps.setString(2,autoLog.getReqType());
                ps.setString(3, autoLog.getReqUrl());
                ps.setString(4,autoLog.getReqData());
                ps.setString(5,autoLog.getExpResult());
                ps.setString(6,autoLog.getActResult());
                ps.setInt(7,autoLog.getResult());
                ps.setString(8,autoLog.getExecTime());
                ps.addBatch();
            }
            rowCount = ps.executeBatch();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(ps,null,connection);
        }
        return rowCount;
    }



    /**
     * @Title: handler
     * @Description: 处理从数据库中获取的数据
     * @param: [rs]
     * @return: java.util.List<java.lang.Object>
     * @date 2020/3/30 10:21
     **/
    public static List<Object> handler(ResultSet rs){
        /**  创建对象数组，用来存储映射后的实体类  **/
        ArrayList<Object> objects = new ArrayList<Object>();

        /** 遍历结果集  **/
        try {
            /**  获取列数据 **/
            ResultSetMetaData rsmd = rs.getMetaData();
            /** 获取每行的列数 **/
            int columnCount = rsmd.getColumnCount();
            while (rs.next()){
                AutoLog autoLog = new AutoLog();
                for (int i = 0; i < columnCount; i++){
                    /**  获取每列的列名 **/
                    String columnName = rsmd.getColumnName(i);
                    /**  获取列值  **/
                    Object value = rs.getObject(columnName);

                    /**  获取实体类字段-通过列名 **/
                    Field field = AutoLog.class.getDeclaredField(columnName);
                    /**  一定要设置为属性可修改  **/
                    field.setAccessible(true);
                    field.set(autoLog,field);
                }
                objects.add(autoLog);
            }
        }catch (Exception e){
           e.printStackTrace();
        }

        return objects;
   }
    /**
     * @Title: close
     * @Description: 关闭资源
     * @param: [ps, rs, con]
     * @return: void
     * @date 2020/4/1 9:05 下午
     **/
    public static void close(PreparedStatement ps,ResultSet rs,Connection con){
        try {
            if (ps != null){
                ps.close();
            }
            if (rs != null){
                rs.close();
            }
            if (con != null){
                con.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
