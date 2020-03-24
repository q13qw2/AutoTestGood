package com.song.test.http;

/**
 * @ClassName:  Test
 * @Description:
 * @author: Jackie
 * @date:   2020/3/11 10:09 上午
 *
 * @Copyright:www.jackie.com All rights reserved.
 * 注意：本内容仅限于作者使用，禁止外泄以及用于其他的商业目
 */
public class Test {
    public static void main(String[] args) {
        // 发送get请求


        // 发送post请求
        String url_a = "http://www.nuandao.com/public/lazyentrance";
        String param_a = "isajax=1&remember=1&email=asfasfasfasdf@qq.com&password=3333333&agreeterms=1&itype=&book=1&m=0.12248663395993764";
        String s = HttpReqUtil.sendPost(url_a, param_a);

        System.out.println(s);

        String url_b = "http://www.nuandao.com/shopping/cart";
        String param_b = "countdown=1&m=0.6105540007303787";
        String s2 = HttpReqUtil.sendPost(url_b, param_b);

        System.out.println(s2);


    }
}
