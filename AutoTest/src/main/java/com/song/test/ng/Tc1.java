package com.song.test.ng;

import com.song.test.model.AutoLog;
import com.song.test.poi.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.song.test.utils.JdbcUtils;
import org.testng.internal.reflect.MethodMatcherException;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: 测试类
 * @Description:用来执行TestNG框架
 * @author: chenlibei
 * @date: 20200227 13:53
 * <p>
 * one
 * @Copyright: 2020 www.ftsafe.com All rights reserved.
 * 注意：本内容仅限于测试练习使用，禁止外泄以及用于其他的商业目
 */
public class Tc1 {

    @Test(dataProvider = "testCaseData",expectedExceptions = MethodMatcherException.class)
    public void case01(String one,String two){
        System.out.println("num:"+ one + "two：" + two);
   }

   @DataProvider(name = "testCaseData",parallel = false)
   public Object[][] dp() {
       ExcelUtil excelUtil = new ExcelUtil("C:\\Users\\JackieSong\\Desktop\\123.xlsx",2);
       Object[][] arrayCellValue = excelUtil.getArrayCellValue(1);

       return arrayCellValue;
   }

    public static void main(String[] args) {

        // 插入数据
        jdbcUpdate();

    }

    public static void jdbcUpdate(){
        // 测试 jdbcUpdate方法
//        String updateSql = "update autolog set testCase='ceshi' where id = ?";
        String updateInsertInto = "INSERT INTO autolog(testCase,reqType,reqUrl,reqData,expResult,actResult,result,execTime) values(?,?,?,?,?,?,?,?)";
        List<AutoLog> list = new ArrayList<AutoLog>();
        AutoLog autoLog = new AutoLog();
        autoLog.setTestCase("test1");
        autoLog.setReqType("GET");
        autoLog.setReqUrl("test1");
        autoLog.setReqData("test1");
        autoLog.setExpResult("test1");
        autoLog.setResult(0);
        autoLog.setExecTime("2020-03-30");
        list.add(autoLog);

        AutoLog autoLog1 = new AutoLog();
        autoLog1.setTestCase("test2");
        autoLog1.setReqType("GET");
        autoLog1.setReqUrl("test2");
        autoLog1.setReqData("test2");
        autoLog1.setExpResult("test2");
        autoLog1.setResult(0);
        autoLog1.setExecTime("2020-03-30");
        list.add(autoLog1);

        int[] rowCount = JdbcUtils.jdbcUpdateBatch(list, updateInsertInto);
        System.out.println("共插入"+rowCount+"条数据");
    }

    public static void jdbcQuery(){
        List<Object> query = JdbcUtils.jdbcQuery("select * from autolog where id = 2");
        for (Object obj : query) {
            AutoLog autoLog = (AutoLog) obj;
            System.out.println(autoLog.getExecTime());
        }
    }

}
