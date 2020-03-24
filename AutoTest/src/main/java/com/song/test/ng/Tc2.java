package com.song.test.ng;

import com.song.test.poi.ExcelUtil;
import com.song.test.utils.Utils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @ClassName: testng
 * @Description:用来验证testNG
 * @author: chenlibei
 * @date: 20200228 1143
 * <p>
 * testNG
 * @Copyright: 2020 www.ftsafe.com All rights reserved.
 * 注意：本内容仅限于测试练习使用，禁止外泄以及用于其他的商业目
 */
public class Tc2 {

    private String fileName;

    @Test(dataProvider = "testResouse")
    public void test(String one){
        System.out.println(one);
    }



    @Parameters({"fileNameParameter"})
    @BeforeTest()
    public void setFileNameResour(String name) {
        this.fileName = name;
    }

    @DataProvider(name = "testResouse")
    public Object[][] dataResouse(){
        Object[][] datas = new ExcelUtil(this.fileName,2).getArrayCellValue(1);
        Utils.outPutTwoArrayToString(datas);
        return datas;
    }

}
