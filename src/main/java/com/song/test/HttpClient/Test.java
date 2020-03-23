package com.song.test.HttpClient;

public class Test {

    /**
     * @Description: TODU
     * @param:
     * @return:
     * @date 2020/3/6 16:28
     */
    public static void main(String[] args) {
        String url = "https://iotoperation-testing.cloudentify.com/operationflat/login!login.action";
        String paramer = "redirect_uri=&app_key=&queryForm.localAuth=0&queryForm.userId=q13qw2%40vip.qq.com&queryForm.password=123456&queryForm.checkCode=8616";
        String value = HttpReqUtil.sendPost(url, paramer);
        System.out.println(value);

    }
}