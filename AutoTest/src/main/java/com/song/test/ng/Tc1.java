package com.song.test.ng;

import com.song.test.poi.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.song.test.utils.JdbcUtils;
import org.testng.internal.reflect.MethodMatcherException;

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
        JdbcUtils.query();
    }
}
