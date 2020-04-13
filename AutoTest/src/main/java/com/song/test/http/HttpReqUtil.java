package com.song.test.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
/**
 * @ClassName:  HttpReqUtil
 * @Description:
 * @author: Jackie
 * @date:   2020/3/10 11:15 PM
 *
 * @Copyright:  www.jackie.com All rights reserved.
 * 注意：本内容仅限于作者使用，禁止外泄以及用于其他的商业目
 */
public class  HttpReqUtil {

    /** 实例化cookiesStore对象 */
    private static BasicCookieStore cookieStore = new BasicCookieStore();
    /** httpClient实例化对象 */
    private static CloseableHttpClient httpClient = null;

  /**
   * @Title: sendGet
   * @Description: 发送Get请求
   * @param: [urlName]
   * @return: java.lang.String
   **/
    public static String sendGet(String urlName, String param){

        /** 属性 */
        CloseableHttpResponse response = null;
        String result  = null;
        String reqUrl = urlName + "?" + param;

        // 实例化得到httpclient对象及请求配置
        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();


        try {
            //1.创建httpclient实例对象


            // 2.创建get请求
            HttpGet httpGet = new HttpGet(reqUrl);
            // 2.1 设置请求配置: 请求头，请求超时
            httpReqConfig(httpGet);

            // 3.发送请求
            response = httpClient.execute(httpGet);

            // 4.接收结果
              // 进行判断
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 得到服务器返回的值
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");

            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                // 5.关闭数据流
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 6.返回结果
        return result;
    }

   /**
     * @Title: sendPost
     * @Description: TODU
     * @param: [urlName, paramer]
     * @return: java.lang.String
     * @date 2020/3/10 3:38 PM
    */
    public static String sendPost(String urlName,String param){

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = null;

        // 实例化得到httpclient对象及请求配置

        httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

        try {
            // 1.创建httpClient

            HttpPost httpPost = new HttpPost(urlName);
            // 2.1 设置请求配置：请求头，请求超时
            httpReqConfig(httpPost);

            // 3.设置消息实体
            StringEntity stringEntity = new StringEntity(param);

            if (new ParseJsonToMapUtil().isJsonString(param) ){
                stringEntity.setContentType("application/json; charset=UTF-8");
            }else {
                stringEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            }

            httpPost.setEntity(stringEntity);
            // 4.发送请求并返回结果
            response = httpClient.execute(httpPost);

            // 5.获取结果
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
            }

            // 打印Cookie信息
            List<Cookie> list = cookieStore.getCookies();
            for (Cookie cookie : list) {
                System.out.println(urlName +" ---" +cookie);
            }


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

   /**
    * @Title: httpReqConfig
    * @Description: TODU
    * @param: [httpRequest]
    * @return: void
    * @date 2020/3/10 3:57 PM
    **/
    public static void httpReqConfig(HttpRequestBase httpRequest){

        // 添加请求头header信息

        httpRequest.setHeader("Accept", "application/json, text/javascript; q=0.01");
        httpRequest.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");

        // 添加请求超时配置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).build();
        httpRequest.setConfig(requestConfig);

    }

}
