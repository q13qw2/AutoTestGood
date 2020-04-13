package com.song.test.utils;
import com.alibaba.fastjson.JSONPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

   public static String compareResultRegex = "([\\$\\.a-zA-Z0-9]+)=(\\w+)";

    public static Matcher getMatcher(String regex,String data){
        /**  初始化返回值  **/
        Pattern compile = null;
        Matcher matcher = null;

        try {
            /**  建立正则规范  **/
            compile = Pattern.compile(regex);
            /**  执行规范  **/
            matcher = compile.matcher(data);
        }catch (Exception e){
            e.printStackTrace();
        }
        return matcher;
    }

    // expResult  ：Excel
    // actResult : 网络返回数据
    public static void compareResult(String expResult,String actResult){

        /**  建立字符串正则表达式  **/
        Matcher matcher = getMatcher(compareResultRegex, expResult);
        /**  判断是否匹配成功  **/
        while (matcher.find()){
            // 取出网络的 $.status = 的 Value  :   1
            Assert.assertEquals(JSONPath.read(actResult,matcher.group(1).toString()),matcher.group(2).toString());
        }
    }

    public static int compareResultToDb(String expResult,String actResult){

        /**  初始返回值  **/
        int flag = 0;
        /**  存储对比结果  **/
        ArrayList<Integer> objects = new ArrayList<Integer>();

        /**  建立字符串正则表达式  **/
        Matcher matcher = getMatcher(compareResultRegex, expResult);
        /**  判断是否匹配成功  **/
        while (matcher.find()) {

           try {
               flag = JSONPath.read(actResult,matcher.group(1)).toString().equals(matcher.group(2)) ? 1 : 0;
           }catch (Exception e){
               e.printStackTrace();
           }

            objects.add(flag);
        }
        /**  如果有一个与预期一致，则判定通过  **/
        if (!objects.contains(0)){
            flag = 1;
        }
        return flag;
    }
}
