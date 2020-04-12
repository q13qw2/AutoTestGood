package com.song.test.ng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.song.test.http.HttpReqUtil;
import com.song.test.model.AutoLog;
import com.song.test.poi.ExcelUtil;
import com.song.test.utils.DpcpUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRun {

    public static ArrayList<AutoLog> autoLogs = new ArrayList<AutoLog>();

    /**
     * @Title: dataProdite
     * @Description: 提供测试数据
     * @param: []
     * @return: java.lang.Object[][]
     * @date 2020/4/6 10:33 上午
     **/
    @DataProvider(name = "testData",parallel = false)
    public Object[][] dataProvider() throws Exception{
        return ExcelUtil.getArrayCellValue(0);
    }

    @DataProvider(name = "test001")
    public Object[] getStringTest(){
        return new String[]{"test1", "test2"};
    }
    /**
     * @Title: test
     * @Description: TODU
     * @param: [id, Test_is_exec, TestCase, Req_type, Req_host, Req_interface, Req_data, Result_expected, Is_Dep, Dep_key]
     * @return: void
     * @date 2020/4/6 11:46 上午
     **/

    @Test(dataProvider = "testData")
    public void test(String id,String isExec,String testCase,String reqType,String reqHost,String reqInterface,String reqData,String expResult,String isDep,String depKey){

        // 定义url请求地址
        String url = reqHost + reqInterface;
        String result = "123";
        // 判断用例是否执行
        if ("YES".equals(isExec)){
            // 判断请求类型
            if ("GET".equals(reqType)){
                result = HttpReqUtil.sendGet(url,reqData);
                System.out.println("11111111"+result);
            }else if ("POST".equals(reqType)){
                result = HttpReqUtil.sendPost(url,reqData);
            }
        }else {
            System.out.println("该条用例不执行");
        }
        // 将结果封装成实例类，存储到数据库


        result = "$.status=1;$.vid.id=2090";
        System.out.println("result："+result);
        // 使用正则表达式进行获取结果
         /** 建立正则表达式字符串 **/
         String regex = "([\\$\\.a-zA-Z0-9]+)=(\\w)";
         
         /**  建立正则规范  **/
        Pattern compile = Pattern.compile(regex);

        /**  执行规范-匹配 **/
        Matcher matcher = compile.matcher(result);

        /**  判断匹配是否成功 **/
        if (matcher.matches()){
            System.out.println("result格式合法");
        }else {
            System.out.println("result格式不合法");
        }

        while (matcher.find()){
            System.out.println("组的长度:"+matcher.groupCount());
            System.out.println("组的内容:"+matcher.group());
            System.out.println("第1组的内容:"+matcher.group(1));
            System.out.println("第2组的内容:"+matcher.group(2));
        }

    }

    @AfterTest
    public void afterTest(){
        // 存储到数据库中
        DpcpUtils.dpcpBatchUpdate(autoLogs,"INSERT INTO auto00");
    }

}
