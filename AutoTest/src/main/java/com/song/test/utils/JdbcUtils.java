package com.song.test.utils;

import java.sql.*;

public class JdbcUtils {

    /** 封装属性 **/
    private static String url= "jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8&useSSL=false";
    private static String user = "root";
    private static String password = "12345678";
    private static Connection connection = null;

    /** 静态代码块，只执行一次 **/
    static{
        try {
            // step1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            // step2 DriverManager建立连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 查询 **/
    public static void query() {
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {

            // step3 对sql进行预编译，参数化及存储
            ps = connection.prepareStatement("select * from autolog where id = ?");
            ps.setInt(1,1);

            //step4 执行sql进行查询
            resultSet = ps.executeQuery();

            // step5 解析数据
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getInt(9));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // step6 关闭资源,采用栈原则，后进先出
            close(ps,resultSet,connection);

        }
    }

    public static void close(PreparedStatement ps,ResultSet rs,Connection con){
        try {
            if (!ps.isClosed()){
                ps.close();
            }
            if (!rs.isClosed()){
                rs.close();
            }
            if (!con.isClosed()){
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
