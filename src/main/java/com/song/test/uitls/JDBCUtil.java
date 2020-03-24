package com.song.test.uitls;

import java.sql.*;

public class JDBCUtil {

    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/interface?characterEncoding=UTF-8";
        String userName = "root";
        String passWord = "123321";


        // step1 加载驱动
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // step2 DriverManager建立连接
            connection = DriverManager.getConnection(url, userName, passWord);

            // step3 对sql进行预编译，参数化及存储
            ps = connection.prepareStatement("select * from  autolog where id=?");
            ps.setInt(1,1);

            // step4 执行sql进行查询
            resultSet = ps.executeQuery();

            // step5 解析数据
            while (resultSet.next()){
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));
                System.out.println(resultSet.getInt(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // step6 关闭对象，按照栈的顺序，后进先出
            try {
                resultSet.close();
                ps.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
