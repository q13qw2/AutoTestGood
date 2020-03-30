package com.song.test.utils;

import com.song.test.model.AutoLog;
import jdk.nashorn.internal.objects.NativeUint8Array;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtils {

    /** 封装属性 **/
    // Mac
//    private static String url= "jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8&useSSL=false";
//    private static String password = "12345678"

     // Windows
    private static String url= "jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8";
    private static String user = "root";

    private static String password = "123321";
    private static Connection connection = null;


    /** 静态代码块，只执行一次 **/
    static{
        try {
            // step1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // step2 DriverManager建立连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * @Title: handler
     * @Description: 处理从数据库中获取的数据
     * @param: [rs]
     * @return: java.util.List<java.lang.Object>
     * @date 2020/3/30 10:21
     **/
    public static List<Object> handler(ResultSet resultSet){
        // 创建List集合，用来存储赋值后的对象
        List<Object> objectList = new ArrayList<Object>();

        try {
            // step5 解析数据
            ResultSetMetaData rsmd = resultSet.getMetaData(); // 数据库中：列
            int columnCount = rsmd.getColumnCount();
            // 遍历数据库的数据
            while (resultSet.next()){
                Object obj = AutoLog.class.newInstance();
                for (int column = 1; column <= columnCount; column++) { // 遍历行的所有列
                    String columnName = rsmd.getColumnName(column);
                    Object value = resultSet.getObject(columnName);
                    // 得到实体类字段
                    Field field = obj.getClass().getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(obj,value);
                }
                // 将赋值后的对象存储到List集合
                objectList.add(obj);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return objectList;
    }

    /**
     * @Title: jdbcUpdate
     * @Description: 更新
     * @param: [sql]
     * @return: int
     * @date 2020/3/30 14:26
     **/
    public static int jdbcUpdate(String sql){
        PreparedStatement ps = null;
        int resultSetCount = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,1);
            resultSetCount = ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(ps,null,connection);
        }
        return resultSetCount;
    }

    /**
     * @Title: jdbcUpdateBatch
     * @Description:  批量处理insert，update，delete sql。
     * @param: [objectList, sql]
     * @return: int[]
     * @date 2020/3/30 16:46
     **/
    public static int[] jdbcUpdateBatch(List<AutoLog> objectList,String sql){
        PreparedStatement ps = null;
        int[] rowCount = null;
        try {
            ps = connection.prepareStatement(sql);
            for (AutoLog autoLog : objectList) {
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
     * @Title: jdbcQuery
     * @Description: 查询
     * @param: [sql]
     * @return: java.util.List<java.lang.Object>
     * @date 2020/3/30 14:28
     **/
    public static List<Object> jdbcQuery(String sql) {
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<Object> objectList = null;

        try {
            // 对sql进行预编译，参数化及存储
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

    /**
     * @Title: close
     * @Description:关闭资源
     * @param: [ps, rs, con]
     * @return: void
     * @date 2020/3/30 11:06
     **/
    public static void close(PreparedStatement ps,ResultSet rs,Connection con){
        try {
            if (ps!=null){
                ps.close();
            }
            if (rs!=null){
                rs.close();
            }
            if (con!=null){
                con.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
