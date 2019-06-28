package com.raiden.homework.netty.minitomcat.tomcat.http;

import com.raiden.homework.netty.minitomcat.tomcat.controller.TestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/6/27
 */
public class MyHttpServlet {
    public void service(MyRequest request, MyResponse response) {
        System.out.println(request.getUrl());
        if ("GET".equals(request.getMethod().toUpperCase())) {
            doGet(request, response);
        } else if ("POST".equals(request.getMethod().toUpperCase())) {
            doPost(request, response);
        }

    }

    TestController controller = new TestController();

    private void doPost(MyRequest request, MyResponse response) {
        response.setResult(controller.test());
    }

    private void doGet(MyRequest request, MyResponse response) {
        response.setResult(controller.test());
    }
}
