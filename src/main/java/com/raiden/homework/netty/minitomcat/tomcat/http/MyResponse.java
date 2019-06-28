package com.raiden.homework.netty.minitomcat.tomcat.http;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: Raiden
 * Date: 2019/6/27
 */
@Data
@NoArgsConstructor
public class MyResponse {
    String result = "";
    OutputStream outputStream;

    public MyResponse(OutputStream outputStream) {

        this.outputStream = outputStream;
    }

    void write(String content) {
        StringBuilder sb = new StringBuilder("HTTP/1.1 ");
        sb.append("200 OK\n");
        sb.append("Content-Type:text/html;\n");
        sb.append("\r\n");
        sb.append(content);
        try {
            outputStream.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
