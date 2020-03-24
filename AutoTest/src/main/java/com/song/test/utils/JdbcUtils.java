package com.song.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtils {



    public static void main(String[] args) {

        String url = "jdbc:mysql:127.0.0.1:3306/interface?characterEncoding=UTF-8";
        String user = "root";
        String password = "12345678";
        // step1 加载驱动
        try {

            // step1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            // step2 DriverManager建立连接
            Connection connection = DriverManager.getConnection(url, user, password);

            // step3 分析Connection
            connection.prepareSt b

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
